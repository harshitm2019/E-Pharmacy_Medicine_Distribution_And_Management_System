package com.harshit.pharmacy.payment.service.impl;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.payment.dto.PaymentRequest;
import com.harshit.pharmacy.payment.dto.PaymentResponse;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.payment.mapper.PaymentMapper;
import com.harshit.pharmacy.payment.repository.PaymentRepository;
import com.harshit.pharmacy.payment.service.PaymentService;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final SecurityUtils securityUtils;


    @Override
    public PaymentResponse processOnlinePayment(PaymentRequest request) {

        User user = securityUtils.getCurrentUser();

        Order order = orderRepository.findByOrderIdAndUser(request.orderId(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (order.getPaymentStatus() == OrderPaymentStatus.PAID)
            throw new BadRequestException("Payment already completed.");

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Payment cannot be processed for this order.");
        }

        if (request.paymentMethod().equals(String.valueOf(PaymentMethod.COD))) {
            throw new BadRequestException("Cash On Delivery payment is not allowed here.");
        }

        Payment payment = paymentRepository.findByOrder(order)
                .orElseGet(() -> PaymentMapper.buildOnlinePayment(order, PaymentMethod.valueOf(request.paymentMethod())));

        payment.setPaymentMethod(PaymentMethod.valueOf(request.paymentMethod()));

        boolean paymentSuccess = paymentGateway(request);

        if (paymentSuccess) {

            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setPaidDate(LocalDateTime.now());
            order.setPaymentStatus(OrderPaymentStatus.PAID);

        } else {

            payment.setPaymentStatus(PaymentStatus.FAILED);

        }

        Payment savedPayment = paymentRepository.save(payment);
        orderRepository.save(order);
        return PaymentMapper.toPaymentResponse(savedPayment);


    }

    private boolean paymentGateway(PaymentRequest request) {

        return Math.random() < 0.8;

    }

    @Override
    public void collectCodPayment(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new BadRequestException("COD payment can only be collected after delivery.");
        }

        if (order.getPaymentStatus() == OrderPaymentStatus.PAID) {
            throw new BadRequestException("Payment already collected.");
        }

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found."));

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaidDate(LocalDateTime.now());

        order.setPaymentStatus(OrderPaymentStatus.PAID);

        paymentRepository.save(payment);
        orderRepository.save(order);

    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        User user = securityUtils.getCurrentUser();

        return paymentRepository.findByOrderUser(user)
                .stream()
                .map(PaymentMapper::toPaymentResponse)
                .toList();

    }

    @Override
    public PaymentResponse getPayment(Integer paymentId) {

        User user = securityUtils.getCurrentUser();

        Payment payment = paymentRepository
                .findByPaymentIdAndOrderUser(paymentId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found."));

        return PaymentMapper.toPaymentResponse(payment);

    }

    @Override
    public void createCodPayment(Order order) {

        Payment payment = PaymentMapper.buildCodPayment(order);

        paymentRepository.save(payment);

    }

    @Override
    public boolean updatePaymentMethod(Order order, String paymentMethod) {

        Optional<Payment> paymentOptional = paymentRepository.findByOrder(order);

        PaymentMethod newPaymentMethod = PaymentMethod.valueOf(paymentMethod);

        if (paymentOptional.isPresent()) {

            Payment payment = paymentOptional.get();

            payment.setPaymentMethod(newPaymentMethod);
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setPaidDate(null);
            paymentRepository.save(payment);


        } else {

            if (newPaymentMethod == PaymentMethod.COD)
                createCodPayment(order);

        }


        if (newPaymentMethod != PaymentMethod.COD)
            return false;


        if (order.getPrescription() == null)
            return true;



        return order.getPrescription().getStatus() == PrescriptionStatus.APPROVED;

    }
}