package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.CategoryRequest;
import com.example.project_bsdb.dto.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
