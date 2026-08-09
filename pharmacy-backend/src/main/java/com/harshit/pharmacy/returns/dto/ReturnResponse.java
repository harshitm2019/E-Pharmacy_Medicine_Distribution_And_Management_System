package com.harshit.pharmacy.returns.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ReturnResponse(

        Integer returnId,

        Integer orderId,

        String returnReason,

        String returnStatus,

        LocalDate returnDate,

        LocalDateTime processedDate

) {
}
