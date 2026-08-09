package com.harshit.pharmacy.dashboard.dto;

import lombok.Builder;

@Builder
public record AdminDashboardResponse(

        long activeMedicines,

        long totalCategories,

        long activeCustomers,

        long activeDeliveryBoys,

        long pendingOrders,

        long pendingPrescriptions


) {
}