package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.AddressRequest;
import com.example.project_bsdb.dto.Response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(String username,AddressRequest request);

    AddressResponse getAddressById(String username, Long id);

    List<AddressResponse> getMyAddresses(String username);

    AddressResponse updateAddress(String username, Long id, AddressRequest request);

    void deleteAddress(String username, Long id);
}
