package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.ProductImageRequest;
import com.example.project_bsdb.dto.Response.ProductImageResponse;
import com.example.project_bsdb.entities.ProductImageEntity;

public class ProductImageMapper {

    public static ProductImageEntity toEntity(ProductImageRequest request) {

        if (request == null) {
            return null;
        }

        return new ProductImageEntity();
    }

    public static ProductImageResponse toResponseDto(
            ProductImageEntity productImage
    ) {

        if (productImage == null) {
            return null;
        }

        ProductImageResponse response = new ProductImageResponse();

        response.setId(productImage.getId());
        response.setImageUrl(productImage.getImageUrl());
        response.setPublicId(productImage.getPublicId());
        response.setDisplayOrder(productImage.getDisplayOrder());

        if (productImage.getProduct() != null) {
            response.setProductId(productImage.getProduct().getId());
        }

        return response;
    }
}
