package com.harshit.pharmacy.order.repository;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

    Page<Order> findByUser(User user, Pageable pageable);

}
