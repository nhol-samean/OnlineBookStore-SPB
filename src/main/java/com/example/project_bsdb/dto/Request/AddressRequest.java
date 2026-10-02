package com.example.project_bsdb.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    @NotBlank(message = "recipient_name must not be blank")
    private String recipientName;
    @NotBlank(message = "phone must not be blank")
    private String phone;
    @NotBlank(message = "street must not be blank")
    private String street;
    @NotBlank(message = "city must not be blank")
    private String city;
    @NotBlank(message = "city must not be blank")
    private String province;
    private String postalCode;
}
