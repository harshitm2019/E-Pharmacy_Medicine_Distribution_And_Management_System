package com.harshit.pharmacy.delivery.service;

import com.harshit.pharmacy.delivery.dto.*;

import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {

    DeliveryBoyResponse createDeliveryBoy(CreateDeliveryBoyRequest request);

    DeliveryBoyResponse updateDeliveryBoy(Integer deliveryBoyId, UpdateDeliveryBoyRequest request);

    Page<DeliveryBoyResponse> getAllDeliveryBoys(Pageable pageable);

    DeliveryStatusResponse assignDeliveryBoy(AssignDeliveryBoyRequest request);

    DeliveryStatusResponse processDeliveryUpdate(Integer orderId, UpdateDeliveryStatusRequest request);

    DeliveryStatusResponse trackDelivery(Integer orderId);

    Page<DeliveryBoyResponse> getAvailableDeliveryBoys(Pageable pageable);

    DeliveryStatusResponse getDeliveryStatus(Integer orderId);

    Page<DeliveryStatusResponse> getDeliveryStatusByStatus(String status, Pageable pageable);

    void removeDeliveryAssignment(Order order);

    Page<DeliveryStatusResponse> getMyDeliveryOrdersByStatus(String status, Pageable pageable);

    OrderResponse getOrderDetails(Integer orderId);


}