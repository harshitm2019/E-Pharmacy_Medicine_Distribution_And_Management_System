package com.harshit.pharmacy.delivery.service;

import com.harshit.pharmacy.delivery.dto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {

    DeliveryBoyResponse createDeliveryBoy(CreateDeliveryBoyRequest request);

    DeliveryBoyResponse updateDeliveryBoy(Integer deliveryBoyId, UpdateDeliveryBoyRequest request);

    DeliveryBoyResponse getDeliveryBoyById(Integer deliveryBoyId);

    Page<DeliveryBoyResponse> getAllDeliveryBoys(Pageable pageable);

    DeliveryStatusResponse assignDeliveryBoy(AssignDeliveryBoyRequest request);

    DeliveryStatusResponse processDeliveryUpdate(Integer orderId, UpdateDeliveryStatusRequest request);

    DeliveryStatusResponse trackDelivery(Integer orderId);

    Page<DeliveryBoyResponse> getAvailableDeliveryBoys(Pageable pageable);


}