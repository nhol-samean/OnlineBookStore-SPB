package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.UserRequest;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.dto.Response.UserResponse;
import com.example.project_bsdb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User Management APIs")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request) {

        UserResponse user = userService.createUser(request);

        return new ResponseEntity<>(
                ApiResponse.success(
                        "User has been created successfully.", user
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long id) {

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User retrieved successfully", user
                )
        );
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get my profile")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(
            Authentication authentication) {

        UserResponse user = userService.getMyProfile(authentication.getName());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User profile retrieved successfully", user
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Users retrieved successfully", users
                )
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        UserResponse updated = userService.updateUser(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User updated successfully", updated
                )
        );
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Update my profile")
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody UserRequest request) {

        UserResponse updated = userService.updateMyProfile(authentication.getName(), request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User profile updated successfully", updated
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User deleted successfully",
                        null
                )
        );
    }
}