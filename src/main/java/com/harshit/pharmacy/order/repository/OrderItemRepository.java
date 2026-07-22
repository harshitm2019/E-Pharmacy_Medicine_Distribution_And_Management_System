package com.harshit.pharmacy.order.repository;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {


    List<OrderItem> findByOrder(Order order);


}
