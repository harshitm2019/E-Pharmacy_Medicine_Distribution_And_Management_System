package com.harshit.pharmacy.order.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @EntityGraph(attributePaths = {
            "orderItems",
            "orderItems.medicine"
    })
    List<Order> findByUser(User user);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.medicine"})
    Optional<Order> findByOrderIdAndUser(Integer orderId, User user);

    @Query("""
    SELECT o
    FROM Order o
    WHERE (:status IS NULL OR CAST(o.orderStatus AS string) = CAST(:status AS string))
""")
    Page<Order> findByStatus(@Param("status") String status, Pageable pageable);

    Optional<Order> findByOrderIdAndOrderStatus(
            Integer orderId,
            OrderStatus orderStatus
    );
    long countByOrderStatus(OrderStatus orderStatus);

    Page<Order> findByOrderDateGreaterThanEqualAndOrderDateLessThan(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    long countByUser(User user);
    long countByUserAndOrderStatus(User user, OrderStatus orderStatus);
    long countByUserAndPaymentStatus(User user, OrderPaymentStatus paymentStatus);


}
