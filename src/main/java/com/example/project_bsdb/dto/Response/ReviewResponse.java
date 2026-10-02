package com.example.project_bsdb.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;
    private Integer rating;
    private String comment;

    private Long userId;
    private String username;

    private Long productId;
    private String productName;
}
