package com.harshit.pharmacy.order.entity;

import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.prescription.entity.Prescription;
import com.harshit.pharmacy.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_amt", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presc_id")
    private Prescription prescription;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "shipping_address", nullable = false, length = 200)
    private String shippingAddress;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "payment_status", nullable = false)
    private OrderPaymentStatus paymentStatus;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();


}