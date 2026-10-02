package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.ProductImageRequest;
import com.example.project_bsdb.dto.Request.ProductImageUpdateRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.ProductImageResponse;
import com.example.project_bsdb.service.ProductImageService;
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

@Tag(name = "Product Image", description = "Product Image Management APIs")
@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Upload product images")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> createProductImages(
            @Valid @ModelAttribute ProductImageRequest request
    ) {

        List<ProductImageResponse> created = productImageService.createProductImages(request);
        return new ResponseEntity<>(
                ApiResponse.success(
                        "Product images created successfully", created
                ),
                HttpStatus.CREATED
        );
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get product image by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductImageResponse>> getProductImageById(
            @PathVariable Long id) {
        ProductImageResponse productImage = productImageService.getProductImageById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product image retrieved successfully", productImage
                )
        );
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all product images")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getAllProductImages() {
        List<ProductImageResponse> productImages = productImageService.getAllProductImages();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product images retrieved successfully", productImages
                )
        );
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get product images by product ID")
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getProductImagesByProductId(
            @PathVariable Long productId
    ) {

        List<ProductImageResponse> productImages = productImageService.getProductImagesByProductId(productId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product images retrieved successfully for product", productImages
                )
        );
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product image")
    public ResponseEntity<ApiResponse<ProductImageResponse>> updateProductImage(
            @PathVariable Long id,
            @ModelAttribute ProductImageUpdateRequest request
    ) {

        ProductImageResponse updated = productImageService.updateProductImage(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product image updated successfully", updated
                )
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product image by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductImage(
            @PathVariable Long id
    ) {
        productImageService.deleteProductImage(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product image deleted successfully",
                        null
                )
        );
    }
}
