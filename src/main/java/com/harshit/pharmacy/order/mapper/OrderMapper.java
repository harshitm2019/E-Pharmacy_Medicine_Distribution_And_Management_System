package com.harshit.pharmacy.order.mapper;

import com.harshit.pharmacy.order.dto.CalculationResult;
import com.harshit.pharmacy.order.dto.CheckoutResponse;
import com.harshit.pharmacy.order.dto.OrderItemResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class OrderMapper {

    public static CheckoutResponse toCheckoutResponse(Order order, PaymentMethod paymentMethod) {

        return CheckoutResponse.builder()
                .orderId(order.getOrderId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .paymentMethod(paymentMethod.name())
                .message("Order Created Successfully")
                .build();

    }

    public static Order buildOrder(User user, Prescription prescription, String shippingAddress,
                                   BigDecimal totalAmount) {

        return Order.builder()
                .user(user)
                .prescription(prescription)
                .shippingAddress(shippingAddress)
                .totalAmount(totalAmount)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(OrderPaymentStatus.PENDING)
                .build();

    }

    public static List<OrderItem> buildOrderItems(
            Order order,
            CalculationResult calculationResult
    ) {

        return calculationResult.orderItems()
                .stream()
                .map(item -> OrderItem.builder()
                        .order(order)
                        .medicine(item.medicine())
                        .quantity(item.quantity())
                        .subTotal(item.subTotal())
                        .discount(item.discount())
                        .tax(item.tax())
                        .build())
                .toList();

    }

    public static OrderResponse toOrderResponse(Order order) {

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .shippingAddress(order.getShippingAddress())
                .orderDate(order.getOrderDate())
                .items(
                        order.getOrderItems()
                                .stream()
                                .map(OrderMapper::toOrderItemResponse)
                                .toList())

                .build();

    }

    private static OrderItemResponse toOrderItemResponse(OrderItem item) {

        return OrderItemResponse.builder()
                .medicineId(item.getMedicine().getMedicineId())
                .medicineName(item.getMedicine().getMedicineName())
                .quantity(item.getQuantity())
                .subTotal(item.getSubTotal())
                .discount(item.getDiscount())
                .tax(item.getTax())
                .build();

    }

}
