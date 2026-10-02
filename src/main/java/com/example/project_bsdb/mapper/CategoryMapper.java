package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.CategoryRequest;
import com.example.project_bsdb.dto.Response.CategoryResponse;
import com.example.project_bsdb.entities.CategoryEntity;

public class    CategoryMapper {

    public static CategoryEntity toEntity(CategoryRequest requestDto) {
        if (requestDto == null) {
            return null;
        }
        CategoryEntity category = new CategoryEntity();
        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());
        return category;
    }

    public static CategoryResponse toResponseDto(CategoryEntity category) {
        if (category == null) {
            return null;
        }
        CategoryResponse responseDto = new CategoryResponse();
        responseDto.setId(category.getId());
        responseDto.setName(category.getName());
        responseDto.setDescription(category.getDescription());
        responseDto.setCreated_at(category.getCreated_at());
        responseDto.setUpdated_at(category.getUpdated_at());
        return responseDto;
    }
}