package com.example.project_bsdb.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String author;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private Long categoryId;

    private String categoryName;

    private List<ProductImageResponse> images;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
