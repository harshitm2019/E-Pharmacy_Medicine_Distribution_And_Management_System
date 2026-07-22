package com.harshit.pharmacy.payment.repository;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByOrder(Order order);

}

