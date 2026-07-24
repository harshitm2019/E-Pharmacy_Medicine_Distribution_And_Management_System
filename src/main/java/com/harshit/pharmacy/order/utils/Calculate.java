package com.harshit.pharmacy.order.utils;


import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.order.dto.CheckoutMedicine;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;


public class Calculate {

    public static BigDecimal calculateTotalAmount(
            CheckoutRequest request,
            Map<Integer, CheckoutMedicine> medicines) {

        BigDecimal total = BigDecimal.ZERO;

        for (CartItemRequest cartItem : request.items()) {

            CheckoutMedicine checkoutMedicine = medicines.get(cartItem.medicineId());

            BigDecimal sellingPrice = checkoutMedicine.sellingPrice();

            BigDecimal quantity = BigDecimal.valueOf(cartItem.quantity());

            total = total.add(sellingPrice.multiply(quantity));

        }

        return total;

    }
}
