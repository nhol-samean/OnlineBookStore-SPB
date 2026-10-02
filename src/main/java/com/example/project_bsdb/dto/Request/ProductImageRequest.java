package com.example.project_bsdb.dto.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequest {

    @NotNull(message = "product_id must not be null")
    private Long productId;

    @NotEmpty(message = "images must not be empty")
    private List<MultipartFile> images;
}