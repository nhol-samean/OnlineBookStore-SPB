package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.AddressRequest;
import com.example.project_bsdb.dto.Response.AddressResponse;
import com.example.project_bsdb.entities.AddressEntity;
public class AddressMapper {
    public static AddressEntity toEntity(AddressRequest request) {

        if (request == null) {
            return null;
        }

        AddressEntity address = new AddressEntity();
        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setPostalCode(request.getPostalCode());
        return address;
    }
    public static AddressResponse toResponseDto(
            AddressEntity address) {

        if (address == null) {
            return null;
        }
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setRecipientName(address.getRecipientName());
        response.setPhone(address.getPhone());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setProvince(address.getProvince());
        response.setPostalCode(address.getPostalCode());

        if (address.getUser() != null) {
            response.setUserId(address.getUser().getId());
        }
        return response;
    }
}


