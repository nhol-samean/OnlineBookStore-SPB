package com.example.project_bsdb.service.Impl;

import com.example.project_bsdb.dto.Request.LoginRequest;
import com.example.project_bsdb.dto.Request.RegisterRequest;
import com.example.project_bsdb.dto.Request.UserRequest;
import com.example.project_bsdb.dto.Response.LoginResponse;
import com.example.project_bsdb.dto.Response.MessageResponse;
import com.example.project_bsdb.dto.Response.UserResponse;
import com.example.project_bsdb.entities.RoleEntity;
import com.example.project_bsdb.entities.UserEntity;
import com.example.project_bsdb.enums.RoleUser;
import com.example.project_bsdb.exception.BadRequestException;
import com.example.project_bsdb.exception.ResourceNotFoundException;
import com.example.project_bsdb.mapper.UserMapper;
import com.example.project_bsdb.repo.RoleRepository;
import com.example.project_bsdb.repo.UserRepository;
import com.example.project_bsdb.security.JwtService;
import com.example.project_bsdb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public MessageResponse registerUser(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already in use");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        RoleEntity roleUser = roleRepository
                .findByName(RoleUser.ROLE_USER)
                .orElseGet(() ->
                        roleRepository.save(
                                new RoleEntity(RoleUser.ROLE_USER)
                        )
                );

        UserEntity user = new UserEntity();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.getRoles().add(roleUser);

        userRepository.save(user);

        return new MessageResponse("User created successfully");
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new BadCredentialsException("Username not found");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Wrong password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        Set<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new LoginResponse(
                userDetails.getUsername(),
                token,
                roles
        );
    }
    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new BadRequestException(
                    "Username '" + request.getUsername() + "' already exists");
        }
        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new BadRequestException("Email '" + request.getEmail() + "' already exists");
        }
        UserEntity user = UserMapper.toEntity(request);
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        RoleEntity roleUser =
                roleRepository
                        .findByName(RoleUser.ROLE_USER)
                        .orElseGet(() ->
                                roleRepository.save(
                                        new RoleEntity(
                                                RoleUser.ROLE_USER
                                        )
                                )
                        );
        user.getRoles().add(roleUser);
        UserEntity saved = userRepository.save(user);
        return UserMapper.toResponseDto(saved);
    }
    @Override
    public UserResponse getUserById(Long id) {

        UserEntity user = userRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found with id: " + id));

        return UserMapper.toResponseDto(user);
    }

    @Override
    public UserResponse getMyProfile(String username) {

        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username: " + username
                        )
                );

        return UserMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    @Override
    public UserResponse updateUser(
            Long id,
            UserRequest request) {
        UserEntity user = userRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found with id: " + id));
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(
                request.getEmail())) {
            throw new BadRequestException("Email '" + request.getEmail() +"' already exists");
        }
        if (!user.getUsername().equalsIgnoreCase(request.getUsername()) && userRepository.existsByUsername(
                request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' already exists");
        }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        UserEntity updated = userRepository.save(user);

        return UserMapper.toResponseDto(updated);
    }

    @Override
    public UserResponse updateMyProfile(
            String username,
            UserRequest request
    ) {

        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with username: " + username
                        )
                );

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new BadRequestException(
                    "Email '" + request.getEmail() + "' already exists"
            );
        }

        if (!user.getUsername().equalsIgnoreCase(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {

            throw new BadRequestException(
                    "Username '" + request.getUsername() + "' already exists"
            );
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        UserEntity updated = userRepository.save(user);
        return UserMapper.toResponseDto(updated);
    }

    @Override
    public void deleteUser(Long id) {

        UserEntity user = userRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }
}
