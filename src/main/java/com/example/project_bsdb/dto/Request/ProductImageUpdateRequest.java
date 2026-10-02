package com.example.project_bsdb.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageUpdateRequest {
    @NotNull(message = "payment method must not be null")
    private MultipartFile file;
}
