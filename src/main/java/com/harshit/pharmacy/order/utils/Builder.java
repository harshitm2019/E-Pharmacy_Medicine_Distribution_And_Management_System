package com.harshit.pharmacy.order.utils;

import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import com.harshit.pharmacy.redis.dto.ReservationItem;
import com.harshit.pharmacy.redis.dto.ReservationRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Builder {

    public ReservationRequest buildReservationRequest(
            Integer userId,
            CheckoutRequest request,
            Map<Integer, Medicine> medicines) {

        List<ReservationItem> items = new ArrayList<>();

        for (CartItemRequest cartItem : request.items()) {

            Medicine medicine = medicines.get(cartItem.medicineId());

            items.add(ReservationItem.builder()
                            .medicineId(medicine.getMedicineId())
                            .medicineName(medicine.getMedicineName())
                            .quantity(cartItem.quantity())
                            .build()
            );
        }

        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        return ReservationRequest.builder()
                .userId(userId)
                .items(items)
                .expiresAt(expiry)
                .expiresAtEpoch(expiry.atZone(java.time.ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli())
                .build();
    }

    public List<OrderItem> buildOrderItems(
            Order order,
            CheckoutRequest request,
            Map<Integer, Medicine> medicines) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemRequest cartItem : request.items()) {

            Medicine medicine = medicines.get(cartItem.medicineId());

            BigDecimal quantity = BigDecimal.valueOf(cartItem.quantity());

            BigDecimal subTotal = medicine.getPrice().multiply(quantity);

            BigDecimal discount = medicine.getDiscount();

            /*
             * Tax calculation can be changed later.
             * Currently assumed as zero because
             * Medicine entity doesn't contain GST.
             */
            BigDecimal tax = BigDecimal.ZERO;

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .medicine(medicine)
                    .quantity(cartItem.quantity())
                    .subTotal(subTotal)
                    .discount(discount)
                    .tax(tax)
                    .build();

            orderItems.add(item);
        }

        return orderItems;
    }


}
