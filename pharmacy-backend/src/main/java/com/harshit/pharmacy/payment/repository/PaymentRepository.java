package com.harshit.pharmacy.payment.repository;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.entity.Payment;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByOrder(Order order);

    List<Payment> findByOrderUser(User user);

    Optional<Payment> findByPaymentIdAndOrderUser(Integer paymentId, User user);
}

