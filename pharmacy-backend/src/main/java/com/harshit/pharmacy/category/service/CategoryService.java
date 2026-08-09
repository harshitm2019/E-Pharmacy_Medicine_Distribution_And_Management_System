package com.harshit.pharmacy.category.service;

import com.harshit.pharmacy.category.dto.CategoryRequest;
import com.harshit.pharmacy.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(
            Integer categoryId,
            CategoryRequest request);

    void deleteCategory(Integer categoryId);

    List<CategoryResponse> searchCategories(String keyword);

    List<CategoryResponse> getAllCategories();

}
