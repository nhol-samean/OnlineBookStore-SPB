package com.example.project_bsdb.mapper;

import com.example.project_bsdb.dto.Request.UserRequest;
import com.example.project_bsdb.dto.Response.UserResponse;
import com.example.project_bsdb.entities.RoleEntity;
import com.example.project_bsdb.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequest request) {

        if (request == null) {
            return null;
        }

        UserEntity user = new UserEntity();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    public static UserResponse toResponseDto(UserEntity user) {

        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {

            RoleEntity role = user.getRoles()
                    .iterator()
                    .next();

            response.setRole(role.getName().name());
        }

        response.setCreated_at(user.getCreated_at());
        response.setUpdated_at(user.getUpdated_at());

        return response;
    }
}
