package com.harshit.pharmacy.dashboard.service.impl;

import com.harshit.pharmacy.category.repository.CategoryRepository;
import com.harshit.pharmacy.dashboard.dto.AdminDashboardResponse;
import com.harshit.pharmacy.dashboard.service.DashboardService;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.repository.PrescriptionRepository;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import com.harshit.pharmacy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Override
    public AdminDashboardResponse getAdminDashboard() {

        return AdminDashboardResponse.builder()
                .activeMedicines(medicineRepository.countByStatus(MedicineStatus.ACTIVE))
                .activeCustomers(userRepository.countByRoleAndStatus(UserRole.CUSTOMER, UserStatus.ACTIVE))
                .activeDeliveryBoys(userRepository.countByRoleAndStatus(UserRole.DELIVERY_BOY, UserStatus.ACTIVE))
                .pendingOrders(orderRepository.countByOrderStatus(OrderStatus.PENDING))
                .pendingPrescriptions(prescriptionRepository.countByStatus(PrescriptionStatus.PENDING))
                .totalCategories(categoryRepository.count())
                .build();

    }
}
