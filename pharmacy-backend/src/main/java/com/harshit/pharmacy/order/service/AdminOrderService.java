package com.harshit.pharmacy.order.service;

import com.harshit.pharmacy.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {


    Page<OrderResponse> getOrders(String status, Pageable pageable);

    void updatePrescriptionStatus(Integer orderId, String status);

    void changeOrderStatus(Integer orderId, String status);

    void cancelOrder(Integer orderId);

}
