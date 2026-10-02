package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.ReviewRequest;
import com.example.project_bsdb.dto.Response.ReviewResponse;
import com.example.project_bsdb.entities.ProductEntity;
import com.example.project_bsdb.entities.ReviewEntity;
import com.example.project_bsdb.entities.UserEntity;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.ReviewMapper;
import com.example.project_bsdb.repo.ProductRepository;
import com.example.project_bsdb.repo.ReviewRepository;
import com.example.project_bsdb.repo.UserRepository;
import com.example.project_bsdb.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public ReviewResponse createReview(
            ReviewRequest request,
            String username
    ) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username " + username
                        )
                );
        ProductEntity product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product not found with id " + request.getProductId()
                )
        );
        if (reviewRepository.findByUserIdAndProductId(
                user.getId(),
                product.getId()
        ).isPresent()) {
            throw new BadRequestException("You have already reviewed this product");
        }
        ReviewEntity review = ReviewMapper.toEntity(request);

        review.setUser(user);
        review.setProduct(product);
        ReviewEntity saved = reviewRepository.save(review);

        return ReviewMapper.toResponseDto(saved);
    }

    @Override
    public ReviewResponse getReviewById(Long id) {

        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id " + id
                        )
                );

        return ReviewMapper.toResponseDto(review);
    }

    @Override
    public List<ReviewResponse> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewsByProductId(
            Long productId
    ) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product not found with id " + productId
            );
        }
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(ReviewMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse updateReview(
            Long id,
            ReviewRequest request,
            String username
    ) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id " + id
                        )
                );
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username " + username
                        )
                );
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You can only update your own review");
        }
        ProductEntity product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product not found with id " + request.getProductId()
                )
        );
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setProduct(product);
        ReviewEntity updated = reviewRepository.save(review);
        return ReviewMapper.toResponseDto(updated);
    }

    @Override
    public void deleteReview(
            Long id,
            String username,
            boolean isAdmin
    ) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Review not found with id " + id
                        )
                );
        if (isAdmin) {
            reviewRepository.delete(review);
            return;
        }
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username " + username
                        )
                );
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You can only delete your own review");
        }

        reviewRepository.delete(review);
    }
}
