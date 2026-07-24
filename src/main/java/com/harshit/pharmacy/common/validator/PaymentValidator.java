package com.harshit.pharmacy.common.validator;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.exception.UnauthorizedException;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.payment.repository.PaymentRepository;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentValidator {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public Order validatePayment(Integer orderId, User user) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found."));

        if (!order.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedException("You are not authorized to pay for this order.");
        }

        if (paymentRepository.findByOrder(order).isPresent()) {
            throw new BadRequestException("Payment already completed for this order.");
        }

        if (order.getPaymentStatus() == OrderPaymentStatus.PAID) {
            throw new BadRequestException("Order is already paid.");
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Payment is not allowed for this order.");
        }

        return order;
    }


}
