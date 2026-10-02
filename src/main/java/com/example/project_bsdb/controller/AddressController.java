package com.example.project_bsdb.controller;

import com.example.project_bsdb.dto.Request.AddressRequest;
import com.example.project_bsdb.dto.Response.AddressResponse;
import com.example.project_bsdb.dto.Response.ApiResponse;
import com.example.project_bsdb.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Address", description = "Address Management APIs")
@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create a new address")
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            Authentication authentication,
            @Valid @RequestBody AddressRequest request) {

        String username = authentication.getName();

        AddressResponse created =
                addressService.createAddress(username, request);

        return new ResponseEntity<>(
                ApiResponse.success(
                        "Address created successfully",
                        created
                ),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get my addresses")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getMyAddresses(
            Authentication authentication) {

        String username = authentication.getName();

        List<AddressResponse> addresses =
                addressService.getMyAddresses(username);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Addresses retrieved successfully",
                        addresses
                )
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get address by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddressById(
            Authentication authentication,
            @PathVariable Long id) {

        String username = authentication.getName();

        AddressResponse address =
                addressService.getAddressById(username, id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address retrieved successfully",
                        address
                )
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update address by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {

        String username = authentication.getName();

        AddressResponse updated =
                addressService.updateAddress(
                        username,
                        id,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address updated successfully",
                        updated
                )
        );
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete address by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            Authentication authentication,
            @PathVariable Long id) {

        String username = authentication.getName();

        addressService.deleteAddress(username, id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address deleted successfully",
                        null
                )
        );
    }
}
