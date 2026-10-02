package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.ProductRequest;
import com.example.project_bsdb.dto.Response.ProductImageResponse;
import com.example.project_bsdb.dto.Response.ProductResponse;
import com.example.project_bsdb.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static ProductEntity toEntity(ProductRequest request) {

        if (request == null) {
            return null;
        }
        ProductEntity product = new ProductEntity();

        product.setName(request.getName());
        product.setAuthor(request.getAuthor());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return product;
    }
    public static ProductResponse toResponseDto(ProductEntity product) {

        if (product == null) {
            return null;
        }
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setAuthor(product.getAuthor());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        if (product.getCategory() != null) {

            response.setCategoryId(
                    product.getCategory().getId()
            );

            response.setCategoryName(
                    product.getCategory().getName()
            );
        }
        if (product.getImages() != null) {
            List<ProductImageResponse> images =
                    product.getImages()
                            .stream()
                            .map(ProductImageMapper::toResponseDto)
                            .toList();

            response.setImages(images);

        } else {
            response.setImages(new ArrayList<>());
        }
        response.setCreated_at(product.getCreated_at());
        response.setUpdated_at(product.getUpdated_at());

        return response;
    }
}
