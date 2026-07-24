package com.harshit.pharmacy.order.utils;

import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.order.dto.CheckoutMedicine;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import com.harshit.pharmacy.redis.dto.ReservationItem;
import com.harshit.pharmacy.redis.dto.ReservationRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Builder {

    public static ReservationRequest buildReservationRequest(
            Integer userId,
            CheckoutRequest request,
            Map<Integer, CheckoutMedicine> medicines) {

        List<ReservationItem> items = new ArrayList<>();

        for (CartItemRequest cartItem : request.items()) {

            CheckoutMedicine checkoutMedicine = medicines.get(cartItem.medicineId());

            Medicine medicine = checkoutMedicine.medicine();

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

    public static List<OrderItem> buildOrderItems(
            Order order,
            CheckoutRequest request,
            Map<Integer, CheckoutMedicine> medicines) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemRequest cartItem : request.items()) {

            CheckoutMedicine checkoutMedicine = medicines.get(cartItem.medicineId());

            Medicine medicine = checkoutMedicine.medicine();

            BigDecimal sellingPrice = checkoutMedicine.sellingPrice();

            BigDecimal quantity = BigDecimal.valueOf(cartItem.quantity());

            BigDecimal subTotal = sellingPrice.multiply(quantity);

            BigDecimal tax = BigDecimal.ZERO;

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .medicine(medicine)
                    .quantity(cartItem.quantity())
                    .subTotal(subTotal)
                    .discount(BigDecimal.ZERO)
                    .tax(tax)
                    .build();

            orderItems.add(item);

        }

        return orderItems;

    }


}



