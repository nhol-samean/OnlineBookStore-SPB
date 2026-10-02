package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.AddressRequest;
import com.example.project_bsdb.dto.Response.AddressResponse;
import com.example.project_bsdb.entities.AddressEntity;
import com.example.project_bsdb.entities.UserEntity;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.AddressMapper;
import com.example.project_bsdb.repo.AddressRepository;
import com.example.project_bsdb.repo.UserRepository;
import com.example.project_bsdb.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    @Override
    public AddressResponse createAddress(
            String username,
            AddressRequest request
    ) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        AddressEntity address = AddressMapper.toEntity(request);

        address.setUser(user);

        AddressEntity savedAddress =
                addressRepository.save(address);

        return AddressMapper.toResponseDto(savedAddress);
    }


    @Override
    @Transactional
    public AddressResponse getAddressById(
            String username,
            Long id
    ) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        AddressEntity address =
                addressRepository.findByIdAndUserId(
                                id,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found"
                                )
                        );

        return AddressMapper.toResponseDto(address);
    }


    @Override
    @Transactional
    public List<AddressResponse> getMyAddresses(
            String username
    ) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        return addressRepository
                .findByUserId(user.getId())
                .stream()
                .map(AddressMapper::toResponseDto)
                .toList();
    }


    @Override
    public AddressResponse updateAddress(
            String username,
            Long id,
            AddressRequest request
    ) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        AddressEntity address =
                addressRepository.findByIdAndUserId(
                                id,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found"
                                )
                        );

        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setPostalCode(request.getPostalCode());

        AddressEntity updatedAddress =
                addressRepository.save(address);

        return AddressMapper.toResponseDto(updatedAddress);
    }


    @Override
    public void deleteAddress(
            String username,
            Long id
    ) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        AddressEntity address =
                addressRepository.findByIdAndUserId(
                                id,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found"
                                )
                        );

        addressRepository.delete(address);
    }
}
