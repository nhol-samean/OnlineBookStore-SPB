package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.ProductRequest;
import com.example.project_bsdb.dto.Response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategoryId(Long categoryId);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
