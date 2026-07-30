package com.harshit.pharmacy.payment.entity;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.enums.PaymentMethod;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


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

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "pay_method", nullable = false,columnDefinition = "pay_method_enum")
    private PaymentMethod paymentMethod;

    @Column(name = "amt", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "pay_status", nullable = false,columnDefinition = "payment_status_enum")
    private PaymentStatus paymentStatus;

    @Column(name = "paid_date")
    private LocalDateTime paidDate;



}
