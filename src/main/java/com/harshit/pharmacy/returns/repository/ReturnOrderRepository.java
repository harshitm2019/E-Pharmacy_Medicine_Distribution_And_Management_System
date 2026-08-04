package com.harshit.pharmacy.returns.repository;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.returns.entity.ReturnOrder;
import com.harshit.pharmacy.returns.enums.ReturnStatus;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Integer> {

    boolean existsByOrder(Order order);

    Page<ReturnOrder> findByOrderUser(User user, Pageable pageable);

    Page<ReturnOrder> findByReturnStatus(ReturnStatus returnStatus, Pageable pageable);

}