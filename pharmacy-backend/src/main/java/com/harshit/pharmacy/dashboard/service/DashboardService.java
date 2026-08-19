package com.harshit.pharmacy.dashboard.service;

import com.harshit.pharmacy.dashboard.dto.AdminDashboardResponse;
import com.harshit.pharmacy.dashboard.dto.CustomerDashboardResponse;
import com.harshit.pharmacy.dashboard.dto.DeliveryBoyDashboardResponse;

public interface DashboardService {

    AdminDashboardResponse getAdminDashboard();
    CustomerDashboardResponse getCustomerDashboard();
    DeliveryBoyDashboardResponse getDeliveryBoyDashboard();

}
