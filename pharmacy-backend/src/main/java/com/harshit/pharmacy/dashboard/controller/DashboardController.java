package com.harshit.pharmacy.dashboard.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.dashboard.dto.AdminDashboardResponse;
import com.harshit.pharmacy.dashboard.dto.CustomerDashboardResponse;
import com.harshit.pharmacy.dashboard.dto.DeliveryBoyDashboardResponse;
import com.harshit.pharmacy.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getDashboard() {

        AdminDashboardResponse response = dashboardService.getAdminDashboard();

        return ResponseEntity.ok(

                ApiResponse.success(SuccessMessages.DASHBOARD_FETCHED_SUCCESSFULLY, response)
        );

    }

    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<CustomerDashboardResponse>> getCustomerDashboard() {

        CustomerDashboardResponse response = dashboardService.getCustomerDashboard();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.DASHBOARD_FETCHED_SUCCESSFULLY, response)
        );
    }

    @GetMapping("/delivery-boy")
    public ResponseEntity<ApiResponse<DeliveryBoyDashboardResponse>>
    getDeliveryBoyDashboard() {

        DeliveryBoyDashboardResponse response =
                dashboardService.getDeliveryBoyDashboard();

        return ResponseEntity.status(HttpStatus.OK).body(
                 ApiResponse.success(SuccessMessages.DASHBOARD_FETCHED_SUCCESSFULLY, response)
        );
    }

}