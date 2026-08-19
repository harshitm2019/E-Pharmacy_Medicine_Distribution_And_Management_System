package com.harshit.pharmacy.dashboard.dto;

import lombok.Builder;

@Builder
public record DeliveryBoyDashboardResponse(

        long totalDeliveredOrders,
        long currentAssignedOrders,
        long currentOutForDeliveryOrders
) {
}
