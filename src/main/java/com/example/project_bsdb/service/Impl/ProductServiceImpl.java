package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.ProductRequest;
import com.example.project_bsdb.dto.Response.ProductResponse;
import com.example.project_bsdb.entities.CategoryEntity;
import com.example.project_bsdb.entities.ProductEntity;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.ProductMapper;
import com.example.project_bsdb.repo.CategoryRepository;
import com.example.project_bsdb.repo.ProductRepository;
import com.example.project_bsdb.service.ProductImageService;
import com.example.project_bsdb.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageService productImageService;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        CategoryEntity category = categoryRepository.findById(
                request.getCategoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Category not found with id: "
                                + request.getCategoryId()
                )
        );

        ProductEntity product = ProductMapper.toEntity(request);

        product.setCategory(category);

        ProductEntity savedProduct =
                productRepository.save(product);

        return ProductMapper.toResponseDto(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        return ProductMapper.toResponseDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategoryId(
            Long categoryId
    ) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException(
                    "Category not found with id: " + categoryId
            );
        }

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request
    ) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        CategoryEntity category = categoryRepository.findById(
                request.getCategoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Category not found with id: "
                                + request.getCategoryId()
                )
        );

        product.setName(request.getName());
        product.setAuthor(request.getAuthor());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        ProductEntity updatedProduct =
                productRepository.save(product);

        return ProductMapper.toResponseDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );
        productImageService.deleteProductImagesByProductId(id);

        productRepository.delete(product);
    }
}
