package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.ProductImageRequest;
import com.example.project_bsdb.dto.Request.ProductImageUpdateRequest;
import com.example.project_bsdb.dto.Response.ProductImageResponse;

import java.util.List;

public interface ProductImageService {

    List<ProductImageResponse> createProductImages(ProductImageRequest request);

    ProductImageResponse getProductImageById(Long id);

    List<ProductImageResponse> getAllProductImages();

    List<ProductImageResponse> getProductImagesByProductId(Long productId);

    ProductImageResponse updateProductImage(Long id, ProductImageUpdateRequest request);

    void deleteProductImage(Long id);

    void deleteProductImagesByProductId(Long productId);
}
