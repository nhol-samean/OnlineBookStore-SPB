package com.example.project_bsdb.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String username;
    private String token;
    private Set<String> roles;
}
