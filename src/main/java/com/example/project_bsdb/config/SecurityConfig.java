package com.example.project_bsdb.config;

import com.example.project_bsdb.security.CustomUserDetailsService;
import com.example.project_bsdb.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authentication =
                new DaoAuthenticationProvider(userDetailsService);

        authentication.setPasswordEncoder(passwordEncoder());

        return authentication;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth", "/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/categories/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/products/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/product-images", "/api/product-images/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/product-images/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/product-images/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/product-images/**").authenticated()
                        .requestMatchers("/api/addresses", "/api/addresses/**").authenticated()
                        .requestMatchers("/api/cart", "/api/cart/**").authenticated()
                        .requestMatchers("/api/orders", "/api/orders/**").authenticated()
                        .requestMatchers("/api/payments", "/api/payments/**").authenticated()
                        .requestMatchers("/api/reviews", "/api/reviews/**").authenticated()
                        .requestMatchers("/api/users", "/api/users/**").authenticated()
                        .requestMatchers("/api/admin/dashboard").authenticated()
                        .anyRequest().authenticated()
                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
