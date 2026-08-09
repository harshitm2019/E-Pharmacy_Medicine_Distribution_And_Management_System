package com.harshit.pharmacy.returns.entity;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.returns.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "return_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Integer returnId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(name = "return_reason", nullable = false, length = 500)
    private String returnReason;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "return_status", nullable = false,columnDefinition = "return_status_enum")
    private ReturnStatus returnStatus;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "processed_date")
    private LocalDateTime processedDate;

}