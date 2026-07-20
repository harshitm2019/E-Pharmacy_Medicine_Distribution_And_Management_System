package com.harshit.pharmacy.category.controller;


import com.harshit.pharmacy.category.record.CategoryRequest;
import com.harshit.pharmacy.category.record.CategoryResponse;
import com.harshit.pharmacy.category.service.CategoryService;
import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.category.*;
import com.harshit.pharmacy.common.swagger.constants.SwaggerConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = SwaggerConstants.CATEGORY_TAG,
        description = "APIs for managing medicine categories."
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    @CreateCategoryApi
    @PostMapping("/admin/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(

                ApiResponse.success(SuccessMessages.CATEGORY_CREATED, response)

        );

    }

    @UpdateCategoryApi
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Integer categoryId,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.CATEGORY_UPDATED, response)
        );
    }

    @DeleteCategoryApi
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Integer categoryId) {

        categoryService.deleteCategory(categoryId);

       return ResponseEntity.status(HttpStatus.OK).body(

               ApiResponse.success(SuccessMessages.CATEGORY_DELETED,null)

       );

    }

    @GetAllCategoriesApi
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {

        List<CategoryResponse> response = categoryService.getAllCategories();

       return ResponseEntity.status(HttpStatus.OK).body(

               ApiResponse.success(SuccessMessages.SUCCESS,response)

       );
    }


    @GetCategoryApi
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @PathVariable Integer categoryId) {

        CategoryResponse response = categoryService.getCategoryById(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.SUCCESS,response)

        );
    }


}
