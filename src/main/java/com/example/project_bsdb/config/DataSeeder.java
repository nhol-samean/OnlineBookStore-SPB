package com.example.project_bsdb.config;

import com.example.project_bsdb.entities.RoleEntity;
import com.example.project_bsdb.entities.UserEntity;
import com.example.project_bsdb.enums.RoleUser;
import com.example.project_bsdb.repo.RoleRepository;
import com.example.project_bsdb.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            RoleEntity userRole = roleRepository.findByName(RoleUser.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new RoleEntity(RoleUser.ROLE_USER)));
            RoleEntity adminRole = roleRepository.findByName(RoleUser.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new RoleEntity(RoleUser.ROLE_ADMIN)));

            userRepository.findByUsername("admin").orElseGet(() -> {
                UserEntity user = new UserEntity();
                user.setUsername("admin");
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("967888226"));
                user.getRoles().add(userRole);
                user.getRoles().add(adminRole);
                return userRepository.save(user);
            });
        };
    }
}

