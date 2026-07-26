package com.harshit.pharmacy.order.service;

import com.harshit.pharmacy.order.dto.CheckoutRequest;
import com.harshit.pharmacy.order.dto.CheckoutResponse;
import com.harshit.pharmacy.order.dto.OrderResponse;
import com.harshit.pharmacy.order.dto.UpdateOrderRequest;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {

    CheckoutResponse checkout(CheckoutRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Integer orderId);

    void cancelOrder(Integer orderId);

    void confirmOrder(Integer orderId);

    void updateOrder(Integer orderId, UpdateOrderRequest request, MultipartFile prescriptionFile);

    void processOrderCancellation(Order order);





}
