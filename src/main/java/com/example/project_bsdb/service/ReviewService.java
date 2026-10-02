package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.ReviewRequest;
import com.example.project_bsdb.dto.Response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(ReviewRequest request, String username);
    ReviewResponse getReviewById(Long id);
    List<ReviewResponse> getAllReviews();
    List<ReviewResponse> getReviewsByProductId(Long productId);
    ReviewResponse updateReview(Long id, ReviewRequest request, String username);
    void deleteReview(Long id, String username, boolean isAdmin);
}