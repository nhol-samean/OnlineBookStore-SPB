package com.example.project_bsdb.dto.Request;

import jakarta.validation.constraints.Email;
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
public class UserRequest {

    @NotBlank(message = "username must not be blank")
    @Size(min = 3, max = 100,
            message = "username must be between 3 to 100 characters")
    private String username;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(min = 6, max = 255,
            message = "password must be between 6 to 255 characters")
    private String password;

    @Size(max = 50)
    private String role;
}
