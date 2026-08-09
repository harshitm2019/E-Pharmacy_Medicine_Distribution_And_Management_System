package com.harshit.pharmacy.delivery.entity;

import com.harshit.pharmacy.delivery.enums.DeliveryStatusEnum;
import com.harshit.pharmacy.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "del_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "del_status_id")
    private Integer deliveryStatusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "del_boy_id", nullable = false)
    private DeliveryBoy deliveryBoy;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "current_status", nullable = false,columnDefinition = "delivery_status_enum")
    private DeliveryStatusEnum currentStatus;

    @CreationTimestamp
    @Column(name = "assigned_date", nullable = false,updatable = false)
    private LocalDateTime assignedDate;

    @Column(name = "expected_del_date", nullable = false)
    private LocalDate expectedDeliveryDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}