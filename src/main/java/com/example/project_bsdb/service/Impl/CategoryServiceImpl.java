package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.CategoryRequest;
import com.example.project_bsdb.dto.Response.CategoryResponse;
import com.example.project_bsdb.entities.CategoryEntity;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.CategoryMapper;
import com.example.project_bsdb.repo.CategoryRepository;
import com.example.project_bsdb.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException(
                    "Category with name '" + request.getName() + "' already exists"
            );
        }

        CategoryEntity category = CategoryMapper.toEntity(request);

        CategoryEntity saved = categoryRepository.save(category);

        return CategoryMapper.toResponseDto(saved);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + id)
                );

        return CategoryMapper.toResponseDto(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request
    ) {

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        )
                );

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByName(request.getName())) {

            throw new BadRequestException(
                    "Category with name '" + request.getName() + "' already exists"
            );
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        CategoryEntity updated = categoryRepository.save(category);

        return CategoryMapper.toResponseDto(updated);
    }

    @Override
    public void deleteCategory(Long id) {

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + id
                        )
                );

        categoryRepository.delete(category);
    }
}

