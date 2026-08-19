package com.harshit.pharmacy.dashboard.dto;
import lombok.Builder;

@Builder
public record CustomerDashboardResponse(

        long totalOrders,
        long pendingOrders,
        long deliveredOrders,
        long totalReturns,
        long totalPrescriptions,
        long pendingPayments
) {
}
