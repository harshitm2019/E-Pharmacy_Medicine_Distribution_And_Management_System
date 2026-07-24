package com.harshit.pharmacy.medicine.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record BatchListResponse(

        Integer medicineId,

        String medicineName,

        List<BatchResponse> batches

) {
}
