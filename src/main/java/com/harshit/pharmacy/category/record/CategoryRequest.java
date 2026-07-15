package com.harshit.pharmacy.category.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(


        @NotBlank(message = "Category name is required.")
        @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters.")
        String categoryName,

        @NotBlank(message = "Description is required.")
        @Size(max = 500, message = "Description cannot exceed 500 characters.")
        String description



) {
}
