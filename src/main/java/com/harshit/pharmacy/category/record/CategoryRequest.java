package com.harshit.pharmacy.category.record;

import com.harshit.pharmacy.category.constants.CategoryConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(


        @NotBlank(message = "Category name is required.")
        @Size(min = CategoryConstants.CATEGORY_NAME_MIN_LENGTH,
                    max = CategoryConstants.CATEGORY_NAME_MAX_LENGTH,
                    message = "Category name must be between 3 and 100 characters.")
        String categoryName,

        @NotBlank(message = "Description is required.")
        @Size(max = CategoryConstants.DESCRIPTION_MAX_LENGTH,
                message = "Description cannot exceed 500 characters.")
        String description



) {
}
