package com.example.project_bsdb.service;

import com.example.project_bsdb.dto.Request.LoginRequest;
import com.example.project_bsdb.dto.Request.RegisterRequest;
import com.example.project_bsdb.dto.Request.UserRequest;
import com.example.project_bsdb.dto.Response.LoginResponse;
import com.example.project_bsdb.dto.Response.MessageResponse;
import com.example.project_bsdb.dto.Response.UserResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    MessageResponse registerUser(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    UserResponse getMyProfile(String username);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    UserResponse updateMyProfile(String username, UserRequest request);

    void deleteUser(Long id);
}
