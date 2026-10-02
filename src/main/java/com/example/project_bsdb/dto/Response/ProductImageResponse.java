package com.example.project_bsdb.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageResponse {

    private Long id;
    private String imageUrl;
    private String publicId;
    private Integer displayOrder;
    private Long productId;
}
