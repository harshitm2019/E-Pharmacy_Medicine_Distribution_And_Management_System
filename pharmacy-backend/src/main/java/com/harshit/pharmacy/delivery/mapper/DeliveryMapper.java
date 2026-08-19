package com.harshit.pharmacy.delivery.mapper;

import com.harshit.pharmacy.delivery.dto.CreateDeliveryBoyRequest;
import com.harshit.pharmacy.delivery.dto.DeliveryBoyResponse;
import com.harshit.pharmacy.delivery.dto.DeliveryStatusResponse;
import com.harshit.pharmacy.delivery.entity.DeliveryBoy;
import com.harshit.pharmacy.delivery.entity.DeliveryStatus;
import com.harshit.pharmacy.delivery.enums.DeliveryBoyStatus;
import com.harshit.pharmacy.delivery.enums.DeliveryStatusEnum;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DeliveryMapper {


    public DeliveryBoy toDeliveryBoy(User user, CreateDeliveryBoyRequest createDeliveryBoyRequest) {

          return DeliveryBoy.builder()
                  .user(user)
                  .vehicleNo(createDeliveryBoyRequest.vehicleNo())
                  .status(DeliveryBoyStatus.ACTIVE)
                  .build();
    }

    public DeliveryStatus toDeliveryStatus(Order order,DeliveryBoy deliveryBoy) {


        return DeliveryStatus.builder()
                .order(order)
                .deliveryBoy(deliveryBoy)
                .currentStatus(DeliveryStatusEnum.ASSIGNED)
                .expectedDeliveryDate(LocalDateTime.now().toLocalDate().plusDays(2))
                .build();

    }

    public DeliveryBoyResponse toDeliveryBoyResponse(DeliveryBoy deliveryBoy) {

        return DeliveryBoyResponse.builder()
                .deliveryBoyId(deliveryBoy.getDeliveryBoyId())
                .userId(deliveryBoy.getUser().getUserId())
                .vehicleNo(deliveryBoy.getVehicleNo())
                .status(deliveryBoy.getStatus().name())
                .username(deliveryBoy.getUser().getUsername())
                .build();

    }

    public DeliveryStatusResponse toDeliveryStatusResponse(DeliveryStatus deliveryStatus) {
        DeliveryBoy deliveryBoy = deliveryStatus.getDeliveryBoy();
        User user = deliveryBoy.getUser();

        return DeliveryStatusResponse.builder()
                .orderId(deliveryStatus.getOrder().getOrderId())
                .deliveryStatus(deliveryStatus.getCurrentStatus().name())
                .deliveryBoyId(deliveryBoy.getDeliveryBoyId())
                .deliveryBoyName(user.getUsername())
                .vehicleNo(deliveryBoy.getVehicleNo())
                .assignedDate(deliveryStatus.getAssignedDate())
                .expectedDeliveryDate(deliveryStatus.getExpectedDeliveryDate())
                .build();
    }

}