package com.harshit.pharmacy.returns.repository;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.returns.entity.ReturnOrder;
import com.harshit.pharmacy.returns.enums.ReturnStatus;
import com.harshit.pharmacy.user.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Integer> {

    boolean existsByOrder(Order order);

    Page<ReturnOrder> findByOrderUser(User user, Pageable pageable);

    Page<ReturnOrder> findByReturnStatus(ReturnStatus returnStatus, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT r
    FROM ReturnOrder r
    WHERE r.returnId = :returnId
    """)
    Optional<ReturnOrder> findByIdForUpdate(Integer returnId);

    long countByOrderUser(User user);

}