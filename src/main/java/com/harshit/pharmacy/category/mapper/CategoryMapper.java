package com.harshit.pharmacy.category.mapper;

import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.category.dto.CategoryRequest;
import com.harshit.pharmacy.category.dto.CategoryResponse;

public final class CategoryMapper {

    private CategoryMapper() {}

    public static Category toEntity(CategoryRequest request) {

        return Category.builder()
                .categoryName(request.categoryName().trim())
                .description(request.description().trim())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {

        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public static void updateEntity(Category category,
                                    CategoryRequest request) {

        category.setCategoryName(request.categoryName().trim());
        category.setDescription(request.description().trim());
    }

}
