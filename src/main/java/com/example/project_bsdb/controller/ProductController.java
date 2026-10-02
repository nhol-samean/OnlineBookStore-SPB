package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.ProductRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.ProductResponse;
import com.example.project_bsdb.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product Management APIs")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product with images")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @ModelAttribute ProductRequest request
    ) {

        ProductResponse created = productService.createProduct(request);
        return new ResponseEntity<>(
                ApiResponse.success(
                        "Product created successfully", created
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable Long id
    ) {

        ProductResponse product = productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product retrieved successfully", product
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all products")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {

        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products retrieved successfully",
                        products
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get products by category ID")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategoryId(
            @PathVariable Long categoryId
    ) {

        List<ProductResponse> products = productService.getProductsByCategoryId(categoryId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products retrieved successfully for category", products
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product with optional new images")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductRequest request
    ) {

        ProductResponse updated = productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product updated successfully", updated
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully",
                        null
                )
        );
    }
}
