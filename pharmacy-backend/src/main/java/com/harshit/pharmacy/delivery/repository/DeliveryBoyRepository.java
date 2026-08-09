package com.harshit.pharmacy.delivery.repository;

import com.harshit.pharmacy.delivery.entity.DeliveryBoy;
import com.harshit.pharmacy.delivery.enums.DeliveryBoyStatus;
import com.harshit.pharmacy.delivery.enums.DeliveryStatusEnum;
import com.harshit.pharmacy.user.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Integer> {

    boolean existsByUser(User user);

    boolean existsByVehicleNoIgnoreCase(String vehicleNo);

    @Query("""
    SELECT db
    FROM DeliveryBoy db
    WHERE db.status = :activeStatus
    AND (
        SELECT COUNT(ds)
        FROM DeliveryStatus ds
        WHERE ds.deliveryBoy = db
        AND ds.currentStatus IN :activeStatuses
    ) < :maxActiveOrders
""")
    Page<DeliveryBoy> findAvailableDeliveryBoys(
            @Param("activeStatus") DeliveryBoyStatus activeStatus,
            @Param("activeStatuses") Collection<DeliveryStatusEnum> activeStatuses,
            @Param("maxActiveOrders") long maxActiveOrders,
            Pageable pageable
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
   SELECT d
   FROM DeliveryBoy d
   WHERE d.deliveryBoyId = :deliveryBoyId
   AND d.status = :status
   """)
    Optional<DeliveryBoy> findActiveByIdForUpdate(
            @Param("deliveryBoyId") Integer deliveryBoyId,
            @Param("status") DeliveryBoyStatus status
    );


}