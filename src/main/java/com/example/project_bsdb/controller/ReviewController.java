package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.ReviewRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.ReviewResponse;
import com.example.project_bsdb.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review management APIs")
public class ReviewController {

    private final ReviewService reviewService;
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create a new review")
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication
    ) {

        ReviewResponse created = reviewService.createReview(
                request,
                authentication.getName()
        );

        return new ResponseEntity<>(
                ApiResponse.success(
                        "Review created successfully", created
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get review by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(
            @PathVariable Long id
    ) {

        ReviewResponse review = reviewService.getReviewById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Review retrieved successfully", review
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all reviews")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {

        List<ReviewResponse> reviews = reviewService.getAllReviews();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Reviews retrieved successfully", reviews
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get reviews by product ID")
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByProductId(
            @PathVariable Long productId
    ) {

        List<ReviewResponse> reviews = reviewService.getReviewsByProductId(productId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Reviews retrieved successfully for product", reviews
                )
        );
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Update own review")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication
    ) {

        ReviewResponse updated = reviewService.updateReview(id, request, authentication.getName());
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Review updated successfully", updated
                )
        );
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Delete review")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long id,
            Authentication authentication
    ) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(
                        authority -> authority.getAuthority()
                                .equals("ROLE_ADMIN")
                );

        reviewService.deleteReview(
                id,
                authentication.getName(),
                isAdmin
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Review deleted successfully",
                        null
                )
        );
    }
}
