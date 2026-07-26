package com.harshit.pharmacy.order.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    Page<Order> findByOrderStatusAndPrescriptionStatus(
            OrderStatus orderStatus,
            PrescriptionStatus prescriptionStatus,
            Pageable pageable
    );
    Optional<Order> findByOrderIdAndOrderStatusAndPrescriptionStatus(
            Integer orderId,
            OrderStatus orderStatus,
            PrescriptionStatus prescriptionStatus
    );

    Optional<Order> findByOrderIdAndOrderStatus(
            Integer orderId,
            OrderStatus orderStatus
    );



}
