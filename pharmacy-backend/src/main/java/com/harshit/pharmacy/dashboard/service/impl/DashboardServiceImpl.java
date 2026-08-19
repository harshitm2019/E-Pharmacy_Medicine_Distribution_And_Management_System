package com.harshit.pharmacy.dashboard.service.impl;

import com.harshit.pharmacy.category.repository.CategoryRepository;
import com.harshit.pharmacy.dashboard.dto.AdminDashboardResponse;
import com.harshit.pharmacy.dashboard.dto.CustomerDashboardResponse;
import com.harshit.pharmacy.dashboard.dto.DeliveryBoyDashboardResponse;
import com.harshit.pharmacy.dashboard.service.DashboardService;
import com.harshit.pharmacy.delivery.enums.DeliveryStatusEnum;
import com.harshit.pharmacy.delivery.repository.DeliveryStatusRepository;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import com.harshit.pharmacy.order.enums.OrderPaymentStatus;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.payment.enums.PaymentStatus;
import com.harshit.pharmacy.prescription.enums.PrescriptionStatus;
import com.harshit.pharmacy.prescription.repository.PrescriptionRepository;
import com.harshit.pharmacy.returns.repository.ReturnOrderRepository;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
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
    private final ReturnOrderRepository returnOrderRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final SecurityUtils securityUtils;

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

    @Override
    public CustomerDashboardResponse getCustomerDashboard() {

        User currentUser = securityUtils.getCurrentUser();

        return CustomerDashboardResponse.builder()
                .totalOrders(orderRepository.countByUser(currentUser))
                .totalReturns(returnOrderRepository.countByOrderUser(currentUser))
                .totalPrescriptions(prescriptionRepository.countByUser(currentUser))
                .pendingPayments(orderRepository.countByUserAndPaymentStatus(currentUser, OrderPaymentStatus.PENDING))
                .deliveredOrders(orderRepository.countByUserAndOrderStatus(currentUser, OrderStatus.DELIVERED))
                .pendingOrders(orderRepository.countByUserAndOrderStatus(currentUser,OrderStatus.PENDING))
                .build();

    }

    @Override
    public DeliveryBoyDashboardResponse getDeliveryBoyDashboard() {

        Integer userId = securityUtils.getCurrentUser().getUserId();

        long assignedOrders = deliveryStatusRepository.
                      countByDeliveryBoyUserUserIdAndCurrentStatus(userId, DeliveryStatusEnum.ASSIGNED);

        long outForDeliveryOrders = deliveryStatusRepository.
                     countByDeliveryBoyUserUserIdAndCurrentStatus(userId, DeliveryStatusEnum.OUT_FOR_DELIVERY);

        long deliveredOrders = deliveryStatusRepository.
                     countByDeliveryBoyUserUserIdAndCurrentStatus(userId, DeliveryStatusEnum.DELIVERED);

        return DeliveryBoyDashboardResponse.builder()
                .currentOutForDeliveryOrders(outForDeliveryOrders).currentAssignedOrders(assignedOrders)
                .totalDeliveredOrders(deliveredOrders).build();

    }
}
