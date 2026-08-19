package com.harshit.pharmacy.delivery.repository;

import com.harshit.pharmacy.delivery.entity.DeliveryBoy;
import com.harshit.pharmacy.delivery.entity.DeliveryStatus;
import com.harshit.pharmacy.delivery.enums.DeliveryStatusEnum;
import com.harshit.pharmacy.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Integer> {


    Optional<DeliveryStatus> findByOrder(Order order);

    boolean existsByOrder(Order order);

    @Query("""
    SELECT COUNT(ds)
    FROM DeliveryStatus ds
    WHERE ds.deliveryBoy.deliveryBoyId = :deliveryBoyId
      AND ds.currentStatus IN :statuses
    """)
    long countActiveOrders(
            @Param("deliveryBoyId") Integer deliveryBoyId,
            @Param("statuses") Collection<DeliveryStatusEnum> statuses
    );

    Optional<DeliveryStatus> findByOrderOrderId(Integer orderId);

    Page<DeliveryStatus> findByCurrentStatus(DeliveryStatusEnum status, Pageable pageable);

    void deleteByOrder(Order order);

    long countByDeliveryBoyUserUserIdAndCurrentStatus(Integer userId, DeliveryStatusEnum currentStatus);

    Page<DeliveryStatus> findByDeliveryBoyUserUserIdAndCurrentStatus(
            Integer userId,
            DeliveryStatusEnum currentStatus,
            Pageable pageable
    );

}