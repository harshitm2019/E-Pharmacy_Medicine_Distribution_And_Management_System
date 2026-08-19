package com.harshit.pharmacy.category.controller;


import com.harshit.pharmacy.category.dto.CategoryRequest;
import com.harshit.pharmacy.category.dto.CategoryResponse;
import com.harshit.pharmacy.category.service.CategoryService;
import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(

                ApiResponse.success(SuccessMessages.CATEGORY_CREATED, response)

        );

    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Integer categoryId,
            @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.CATEGORY_UPDATED, response)
        );
    }


    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Integer categoryId) {

        categoryService.deleteCategory(categoryId);

       return ResponseEntity.status(HttpStatus.OK).body(

               ApiResponse.success(SuccessMessages.CATEGORY_DELETED,null)

       );

    }
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {

        List<CategoryResponse> response = categoryService.getAllCategories();

       return ResponseEntity.status(HttpStatus.OK).body(

               ApiResponse.success(SuccessMessages.SUCCESS,response)

       );
    }

    @GetMapping("/categories/search")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> searchCategories(@RequestParam String keyword) {

        List<CategoryResponse> response = categoryService.searchCategories(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.SUCCESS, response)
        );

    }



}
