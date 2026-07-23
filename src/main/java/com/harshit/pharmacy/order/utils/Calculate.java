package com.harshit.pharmacy.order.utils;


import com.harshit.pharmacy.medicine.entity.Medicine;
import com.harshit.pharmacy.order.dto.CartItemRequest;
import com.harshit.pharmacy.order.dto.CheckoutRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class Calculate {

    public BigDecimal calculateTotalAmount(
            CheckoutRequest request,
            Map<Integer, Medicine> medicines) {

        BigDecimal total = BigDecimal.ZERO;

        for (CartItemRequest cartItem : request.items()) {

            Medicine medicine = medicines.get(cartItem.medicineId());

            BigDecimal quantity = BigDecimal.valueOf(cartItem.quantity());

            BigDecimal subTotal = medicine.getPrice().multiply(quantity);

            BigDecimal discount = medicine.getDiscount();

            BigDecimal discountAmount = subTotal.multiply(discount).divide(BigDecimal.valueOf(100));

            BigDecimal taxable = subTotal.subtract(discountAmount);

            BigDecimal tax = BigDecimal.ZERO;

            BigDecimal finalAmount = taxable.add(tax);

            total = total.add(finalAmount);
        }

        return total;

    }
}
