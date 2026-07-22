package com.harshit.pharmacy.category.service.impl;

import com.harshit.pharmacy.category.entity.Category;
import com.harshit.pharmacy.category.mapper.CategoryMapper;
import com.harshit.pharmacy.category.dto.CategoryRequest;
import com.harshit.pharmacy.category.dto.CategoryResponse;
import com.harshit.pharmacy.category.repository.CategoryRepository;
import com.harshit.pharmacy.category.service.CategoryService;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.common.constants.FieldNames;
import com.harshit.pharmacy.common.validator.DuplicateValidator;
import com.harshit.pharmacy.exception.BusinessException;
import com.harshit.pharmacy.exception.ResourceNotFoundException;
import com.harshit.pharmacy.medicine.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DuplicateValidator duplicateValidator;
    private final MedicineRepository medicineRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        String categoryName = request.categoryName().trim();

        duplicateValidator.validate(
                         categoryRepository.existsByCategoryNameIgnoreCase(categoryName),
                         FieldNames.CATEGORY
        );

        Category category = CategoryMapper.toEntity(request);

        return CategoryMapper.toResponse(
                categoryRepository.save(category)
        );

    }

    @Override
    public CategoryResponse updateCategory(Integer categoryId, CategoryRequest request) {

        Category category = getCategory(categoryId);

        String categoryName = request.categoryName().trim();

        Optional<Category> existingCategory =
                categoryRepository.findByCategoryNameIgnoreCase(categoryName);


        duplicateValidator.validate(

                existingCategory.isPresent() && !categoryId.equals(existingCategory.get().getCategoryId()),
                FieldNames.CATEGORY

        );

        CategoryMapper.updateEntity(category, request);

        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(updatedCategory);

    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = getCategory(categoryId);

         if (medicineRepository.existsByCategoryCategoryId(categoryId))
              throw new BusinessException(ErrorMessages.CATEGORY_IN_USE);

        categoryRepository.delete(category);

    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer categoryId) {

        return CategoryMapper.toResponse(
                getCategory(categoryId)
        );

    }


    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll(
                        Sort.by(Sort.Direction.ASC, "categoryName"))
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();

    }

    private Category getCategory(Integer categoryId) {

        return categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.CATEGORY_DOES_NOT_EXIST));

    }
}
