package com.harshit.pharmacy.order.mapper;

import com.harshit.pharmacy.order.dto.CheckoutResponse;
import com.harshit.pharmacy.order.dto.OrderItemResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {



    public Order toEntity(User user , String shippingAddress, BigDecimal totalAmount, Prescription prescription){

           return Order.builder()
                       .user(user)
                       .orderDate(LocalDateTime.now())
                       .shippingAddress(shippingAddress)
                       .totalAmount(totalAmount)
                       .orderStatus(OrderStatus.PENDING)
                       .prescription(prescription)
                       .paymentStatus(OrderPaymentStatus.PENDING)
                       .build();

    }

    public OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(this::toOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .shippingAddress(order.getShippingAddress())
                .orderDate(order.getOrderDate())
                .items(items)
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {

        return OrderItemResponse.builder()
                .medicineId(orderItem.getMedicine().getMedicineId())
                .medicineName(orderItem.getMedicine().getMedicineName())
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal())
                .discount(orderItem.getDiscount())
                .tax(orderItem.getTax())
                .build();
    }


    public CheckoutResponse toCheckoutResponse(Order order,String reservationId) {


        return CheckoutResponse.builder()
                .orderId(order.getOrderId())
                .reservationId(reservationId)
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .message("Order created successfully. Complete payment to confirm your order.")
                .build();

    }
}