package com.harshit.pharmacy.category.dto;

import java.time.LocalDateTime;

public record CategoryResponse(

        Integer categoryId,

        String categoryName,

        String description,

        LocalDateTime createdDate,

        LocalDateTime updatedDate

) {
}
