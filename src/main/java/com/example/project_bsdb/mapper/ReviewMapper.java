package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.ReviewRequest;
import com.example.project_bsdb.dto.Response.ReviewResponse;
import com.example.project_bsdb.entities.ProductEntity;
import com.example.project_bsdb.entities.ReviewEntity;
import com.example.project_bsdb.entities.UserEntity;

public class ReviewMapper {
    public static ReviewEntity toEntity(ReviewRequest request) {

        if (request == null) {
            return null;
        }

        ReviewEntity review = new ReviewEntity();

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return review;
    }
    public static ReviewResponse toResponseDto(ReviewEntity review) {

        if (review == null) {
            return null;
        }

        ReviewResponse response = ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();

        UserEntity user = review.getUser();

        if (user != null) {
            response.setUserId(user.getId());
            response.setUsername(user.getUsername());
        }

        ProductEntity product = review.getProduct();

        if (product != null) {
            response.setProductId(product.getId());
            response.setProductName(product.getName());
        }

        return response;
    }
}
