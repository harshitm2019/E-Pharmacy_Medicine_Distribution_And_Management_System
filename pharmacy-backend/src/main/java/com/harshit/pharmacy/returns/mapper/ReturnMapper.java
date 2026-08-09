package com.harshit.pharmacy.returns.mapper;

import com.harshit.pharmacy.order.entity.Order;
import com.harshit.pharmacy.returns.dto.CreateReturnRequest;
import com.harshit.pharmacy.returns.dto.ReturnResponse;
import com.harshit.pharmacy.returns.entity.ReturnOrder;
import com.harshit.pharmacy.returns.enums.ReturnStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReturnMapper {

    public ReturnOrder toEntity(Order order, CreateReturnRequest request) {

        return ReturnOrder.builder()
                .order(order)
                .returnReason(request.returnReason())
                .returnStatus(ReturnStatus.PENDING)
                .returnDate(LocalDate.now())
                .build();
    }

    public ReturnResponse toResponse(ReturnOrder returnOrder) {

        return ReturnResponse.builder()
                .returnId(returnOrder.getReturnId())
                .orderId(returnOrder.getOrder().getOrderId())
                .returnReason(returnOrder.getReturnReason())
                .returnStatus(returnOrder.getReturnStatus().name())
                .returnDate(returnOrder.getReturnDate())
                .processedDate(returnOrder.getProcessedDate())
                .build();
    }

}