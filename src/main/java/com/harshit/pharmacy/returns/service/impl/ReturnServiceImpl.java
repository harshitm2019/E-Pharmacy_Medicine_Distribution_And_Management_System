package com.harshit.pharmacy.returns.service.impl;

import com.harshit.pharmacy.exception.BadRequestException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.order.enums.OrderStatus;
import com.harshit.pharmacy.order.repository.OrderRepository;
import com.harshit.pharmacy.order.service.OrderService;
import com.harshit.pharmacy.returns.dto.CreateReturnRequest;
import com.harshit.pharmacy.returns.dto.ReturnResponse;
import com.harshit.pharmacy.returns.dto.UpdateReturnStatusRequest;
import com.harshit.pharmacy.returns.entity.ReturnOrder;
import com.harshit.pharmacy.returns.enums.ReturnStatus;
import com.harshit.pharmacy.returns.mapper.ReturnMapper;
import com.harshit.pharmacy.returns.repository.ReturnOrderRepository;
import com.harshit.pharmacy.returns.service.ReturnService;
import com.harshit.pharmacy.security.utils.SecurityUtils;
import com.harshit.pharmacy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReturnServiceImpl implements ReturnService {

    private final ReturnOrderRepository returnOrderRepository;
    private final OrderRepository orderRepository;
    private final ReturnMapper returnMapper;
    private final SecurityUtils securityUtils;
    private final OrderService orderService;

    @Override
    public ReturnResponse createReturn(CreateReturnRequest request) {

        User currentUser = securityUtils.getCurrentUser();

        Order order = orderRepository
                .findByOrderIdAndUser(request.orderId(), currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        validateReturnRequest(order);

        ReturnOrder returnOrder = returnMapper.toEntity(order, request);

        return returnMapper.toResponse(returnOrderRepository.save(returnOrder));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnResponse> getMyReturns(Pageable pageable) {

        User currentUser = securityUtils.getCurrentUser();

        return returnOrderRepository
                .findByOrderUser(currentUser, pageable)
                .map(returnMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnResponse> getReturnsByStatus(String status, Pageable pageable){


        ReturnStatus returnStatus = ReturnStatus.valueOf(status);

        return returnOrderRepository
                .findByReturnStatus(returnStatus, pageable)
                .map(returnMapper::toResponse);

    }

    @Override
    public ReturnResponse updateReturnStatus(Integer returnId, UpdateReturnStatusRequest request) {

        ReturnOrder returnOrder = getReturnOrder(returnId);

        ReturnStatus newStatus = ReturnStatus.valueOf(request.returnStatus());

        validateStatusTransition(returnOrder, newStatus);

        returnOrder.setReturnStatus(newStatus);

        if (newStatus == ReturnStatus.REFUNDED)
            orderService.processReturnedOrder(returnOrder.getOrder());

        return returnMapper.toResponse(returnOrderRepository.save(returnOrder));

    }

    private void validateReturnRequest(Order order) {

        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new BadRequestException("Only delivered orders can be returned.");
        }

        if (returnOrderRepository.existsByOrder(order)) {
            throw new BadRequestException("Return request already exists for this order.");
        }
    }

    private ReturnOrder getReturnOrder(Integer returnId) {

        return returnOrderRepository.findById(returnId)
                .orElseThrow(() -> new ResourceNotFoundException("Return request not found."));
    }

    private void validateStatusTransition(ReturnOrder returnOrder, ReturnStatus newStatus) {

        ReturnStatus currentStatus = returnOrder.getReturnStatus();

        switch (currentStatus) {

            case PENDING -> {

                if (newStatus != ReturnStatus.APPROVED && newStatus != ReturnStatus.REJECTED) {

                    throw new BadRequestException("Return request can only be APPROVED or REJECTED.");
                }

                returnOrder.setProcessedDate(LocalDateTime.now());
            }

            case APPROVED -> {

                if (newStatus != ReturnStatus.REFUNDED) {
                    throw new BadRequestException("Approved return can only be marked as REFUNDED.");
                }
            }

            case REJECTED -> throw new BadRequestException("Rejected return request cannot be updated.");

            case REFUNDED -> throw new BadRequestException("Return request has already been refunded.");

            default -> throw new BadRequestException("Invalid return status transition.");
        }
    }


}
