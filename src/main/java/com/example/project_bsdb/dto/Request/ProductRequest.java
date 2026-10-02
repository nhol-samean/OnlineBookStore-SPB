package com.example.project_bsdb.dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "name must not be blank")
    @Size(max = 150)
    private String name;
    @Size(max = 255, message = "author must be less than 255 characters")
    private String author;

    @Size(max = 2000)
    private String description;

    @NotNull(message = "price must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "price must not be negative")
    private BigDecimal price;

    @NotNull(message = "stock must not be null")
    @Min(value = 0, message = "stock must not be negative")
    private Integer stock;

    @NotNull(message = "category_id must not be null")
    private Long categoryId;

}
