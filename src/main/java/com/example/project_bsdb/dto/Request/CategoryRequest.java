package com.example.project_bsdb.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "name must not be blank")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max=1000,message = "description must be less than 1000")
    private String description;
}
