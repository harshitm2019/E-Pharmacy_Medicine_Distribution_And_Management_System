package com.harshit.pharmacy.order.service.impl;

import com.harshit.pharmacy.common.validator.CheckoutValidator;
import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.order.dto.*;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.mapper.OrderMapper;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.order.service.OrderService;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.prescription.service.PrescriptionService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final BigDecimal GST_PERCENT = BigDecimal.valueOf(18);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private final OrderRepository orderRepository;
    private final MedicineRepository medicineRepository;
    private final SecurityUtils securityUtils;
    private final CheckoutValidator validator;
    private final PaymentService paymentService;
    private final PrescriptionService prescriptionService;
    private final OrderMapper orderMapper;



    @Override
    public CheckoutResponse checkout(CheckoutRequest request) {

        User user = securityUtils.getCurrentUser();

        validator.validateDuplicateMedicineIds(request.items());

        List<Integer> medicineIds = request.items()
                .stream()
                .map(CartItemRequest::medicineId)
                .toList();

        List<Medicine> medicines = medicineRepository.findByMedicineIdIn(medicineIds);

        Map<Integer, Medicine> medicineMap = validator.validateMedicines(request.items(), medicines);


        Prescription prescription = validator.validatePrescription(user, request.prescriptionId(),
                medicineMap.values());


        CalculationResult calculationResult = calculateOrder(request.items(), medicineMap);

        Order order = orderMapper.buildOrder(user, prescription, request.shippingAddress(),
                calculationResult.totalAmount());

        List<OrderItem> orderItems = orderMapper.buildOrderItems(order, calculationResult);

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        if (PaymentMethod.COD.name().equalsIgnoreCase(request.paymentMethod())) {

            paymentService.createCodPayment(savedOrder);

            if (prescription == null) {

                confirmOrder(order.getOrderId());

            }
        }
        return orderMapper.toCheckoutResponse(savedOrder,PaymentMethod.valueOf(request.paymentMethod()));

    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {

        User user = securityUtils.getCurrentUser();

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Integer orderId) {

        User user = securityUtils.getCurrentUser();

        Order order = orderRepository
                .findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        return orderMapper.toOrderResponse(order);

    }

    @Override
    public void cancelOrder(Integer orderId) {

        User user = securityUtils.getCurrentUser();

        Order order = orderRepository.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        processOrderCancellation(order);

    }

    @Override
    public void processOrderCancellation(Order order) {

        OrderStatus status = order.getOrderStatus();

        if (status == OrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled.");
        }

        if (status == OrderStatus.OUT_FOR_DELIVERY || status == OrderStatus.DELIVERED) {
            throw new BadRequestException("Order cannot be cancelled at this stage.");
        }

        if (status == OrderStatus.CONFIRMED || status == OrderStatus.PACKED) {

            restoreStock(order);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    @Override
    public void processReturnedOrder(Order order) {

        restoreStock(order);

    }

    @Override
    public void confirmOrder(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Order is already processed.");
        }

        List<Integer> medicineIds = getMedicineIds(order);

        List<Medicine> medicines = medicineRepository.findAllForUpdate(medicineIds);

        Map<Integer, Medicine> medicineMap = getMedicineMap(medicines);

        for (OrderItem item : order.getOrderItems()) {

            Medicine medicine = medicineMap.get(item.getMedicine().getMedicineId());

            if (medicine == null)
                throw new ResourceNotFoundException("Medicine not found : " + item.getMedicine().getMedicineId());

            validator.validateMedicineIsActiveAndIsExpired(medicine);

            if (medicine.getStockQuantity() < item.getQuantity())
                throw new BadRequestException("Insufficient stock for " + medicine.getMedicineName());

            medicine.setStockQuantity(medicine.getStockQuantity() - item.getQuantity());
        }

        medicineRepository.saveAll(medicines);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

    }

    @Override
    public void updateOrder(Integer orderId, UpdateOrderRequest request, MultipartFile prescriptionFile) {

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository.findByOrderIdAndUser(orderId, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        OrderStatus status = order.getOrderStatus();

        if (status == OrderStatus.CANCELLED || status == OrderStatus.DELIVERED) {
            throw new BadRequestException("Order cannot be updated.");
        }

        if (request != null) {
            if (StringUtils.hasText(request.shippingAddress())) {
                ensureNotOutForDelivery(status, "Shipping address cannot be updated once the order is out for delivery.");
                order.setShippingAddress(request.shippingAddress());
            }

            if (StringUtils.hasText(request.paymentMethod())) {
                ensureOrderIsPending(status, "Payment method can only be updated while the order is pending.");

                if (order.getPaymentStatus() == OrderPaymentStatus.PAID) {
                    throw new BadRequestException("Payment method cannot be changed after successful payment.");
                }

                  if(paymentService.updatePaymentMethod(order, request.paymentMethod()))
                      confirmOrder(orderId);
            }
        }

        if (prescriptionFile != null && !prescriptionFile.isEmpty()) {
            ensureOrderIsPending(status, "Prescription can only be updated while the order is pending.");

            if (order.getPrescription() == null)
                throw new BadRequestException("No prescription is associated with this order.");

            prescriptionService.replacePrescription(order.getPrescription().getPrescriptionId(),
                    prescriptionFile
            );
        }

        orderRepository.save(order);
    }

    private void ensureOrderIsPending(OrderStatus current, String errorMessage) {
        if (current != OrderStatus.PENDING) {
            throw new BadRequestException(errorMessage);
        }
    }

    private void ensureNotOutForDelivery(OrderStatus current, String errorMessage) {
        if (current == OrderStatus.OUT_FOR_DELIVERY) {
            throw new BadRequestException(errorMessage);
        }
    }

    private CalculationResult calculateOrder(List<CartItemRequest> items,
                                             Map<Integer, Medicine> medicineMap) {

        List<OrderItemCalculation> calculations = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemRequest item : items) {

            Medicine medicine = medicineMap.get(item.medicineId());

            BigDecimal subTotal = medicine.getPrice().multiply(BigDecimal.valueOf(item.quantity()));

            BigDecimal discount = subTotal
                    .multiply(medicine.getDiscount())
                    .divide(HUNDRED, 2, RoundingMode.HALF_UP);

            BigDecimal taxableAmount = subTotal.subtract(discount);

            BigDecimal tax = taxableAmount
                    .multiply(GST_PERCENT)
                    .divide(HUNDRED, 2, RoundingMode.HALF_UP);

            BigDecimal finalAmount = taxableAmount.add(tax);

            totalAmount = totalAmount.add(finalAmount);

            calculations.add(OrderItemCalculation.builder()
                            .medicine(medicine)
                            .quantity(item.quantity())
                            .subTotal(subTotal)
                            .discount(discount)
                            .tax(tax)
                            .build()
            );
        }

        return CalculationResult.builder()
                .totalAmount(totalAmount)
                .orderItems(calculations)
                .build();

    }


    private void restoreStock(Order order) {

        List<Integer> medicineIds = getMedicineIds(order);

        List<Medicine> medicines = medicineRepository.findAllForUpdate(medicineIds);

        Map<Integer, Medicine> medicineMap = getMedicineMap(medicines);

        for (OrderItem item : order.getOrderItems()) {

            Medicine medicine = medicineMap.get(item.getMedicine().getMedicineId());

            medicine.setStockQuantity(medicine.getStockQuantity() + item.getQuantity());

        }

        medicineRepository.saveAll(medicines);

    }

    private List<Integer> getMedicineIds(Order order) {

        return   order.getOrderItems()
                .stream()
                .map(item -> item.getMedicine().getMedicineId())
                .toList();

    }

    private Map<Integer, Medicine> getMedicineMap(List<Medicine> medicines) {

             return  medicines.stream()
                     .collect(Collectors.toMap(
                        Medicine::getMedicineId,
                        Function.identity()
                ));

    }

}
