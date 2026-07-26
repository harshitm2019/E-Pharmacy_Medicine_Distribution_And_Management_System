package com.harshit.pharmacy.payment.entity;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "amt", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_date", nullable = false)
    private LocalDateTime paidDate;



}
