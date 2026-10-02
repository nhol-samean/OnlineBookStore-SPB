package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.config.CloudinaryService;
import com.example.project_bsdb.dto.Request.ProductImageRequest;
import com.example.project_bsdb.dto.Request.ProductImageUpdateRequest;
import com.example.project_bsdb.dto.Response.ProductImageResponse;
import com.example.project_bsdb.entities.ProductEntity;
import com.example.project_bsdb.entities.ProductImageEntity;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.ProductImageMapper;
import com.example.project_bsdb.repo.ProductImageRepository;
import com.example.project_bsdb.repo.ProductRepository;
import com.example.project_bsdb.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CloudinaryService cloudinaryService;


    @Override
    @Transactional
    public List<ProductImageResponse> createProductImages(
            ProductImageRequest request
    ) {

        ProductEntity product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product not found with id " + request.getProductId()
                )
        );

        if (request.getImages() == null || request.getImages().isEmpty()) {
            throw new BadRequestException("Images must not be empty");
        }

        int order = productImageRepository.findMaxDisplayOrderByProductId(request.getProductId()) + 1;
        List<ProductImageResponse> responses = new ArrayList<>();

        for (MultipartFile file : request.getImages()) {

            if (file == null || file.isEmpty()) {
                continue;
            }
            Map image = cloudinaryService.uploadImage(file);
            String url = (String) image.get("secure_url");
            if (url == null) {url = (String) image.get("url");}
            String publicId = (String) image.get("public_id");

            ProductImageEntity productImage = new ProductImageEntity();

            productImage.setImageUrl(url);
            productImage.setPublicId(publicId);
            productImage.setDisplayOrder(order++);
            productImage.setProduct(product);

            ProductImageEntity saved = productImageRepository.save(productImage);
            responses.add(ProductImageMapper.toResponseDto(saved));
        }

        return responses;
    }
    @Override
    public ProductImageResponse getProductImageById(Long id
    ) {
        ProductImageEntity productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product image not found with id " + id
                        )
                );
        return ProductImageMapper.toResponseDto(productImage);
    }

    @Override
    public List<ProductImageResponse> getAllProductImages() {

        return productImageRepository.findAll()
                .stream()
                .map(ProductImageMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductImageResponse>
    getProductImagesByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product not found with id " + productId
            );
        }
        return productImageRepository
                .findByProductId(productId)
                .stream()
                .map(ProductImageMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    @Override
    public ProductImageResponse updateProductImage(
            Long id,
            ProductImageUpdateRequest request
    ) {

        ProductImageEntity productImage = productImageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product image not found with id " + id)
                );

        if (request.getFile() == null ||
                request.getFile().isEmpty()) {
            throw new BadRequestException("Image file must not be empty");
        }

        if (productImage.getPublicId() != null) {
            cloudinaryService.deleteImage(productImage.getPublicId());
        }

        Map image = cloudinaryService.uploadImage(request.getFile());

        String url = (String) image.get("secure_url");
        if (url == null) {
            url = (String) image.get("url");
        }

        String publicId = (String) image.get("public_id");
        productImage.setImageUrl(url);
        productImage.setPublicId(publicId);

        ProductImageEntity updated = productImageRepository.save(productImage);
        return ProductImageMapper.toResponseDto(updated);
    }
    @Override
    public void deleteProductImage(Long id) {
        ProductImageEntity productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product image not found with id " + id)
                );
        if (productImage.getPublicId() != null) {
            cloudinaryService.deleteImage(productImage.getPublicId());
        }
        productImageRepository.delete(productImage);
    }

    @Override
    @Transactional
    public void deleteProductImagesByProductId(Long productId) {

        List<ProductImageEntity> images =
                productImageRepository.findByProductId(productId);

        for (ProductImageEntity image : images) {
            if (image.getPublicId() != null) {
                cloudinaryService.deleteImage(
                        image.getPublicId()
                );
            }
        }
        productImageRepository.deleteAll(images);
    }
}
