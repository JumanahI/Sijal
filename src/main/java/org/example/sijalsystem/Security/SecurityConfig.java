package org.example.sijalsystem.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/**",
            "/api/v1/customer/register-customer",
            "/api/v1/hr/register-hr",
            "/api/v1/hr/get-hr",
            "/health"
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/v1/cv/get-all-cv",
            "/api/v1/hr/activate-hr",
            "/api/v1/card/get-cards"
    };

    private static final String[] CUSTOMER_ENDPOINTS = {
            "/api/v1/customer/register-customer",
            "/api/v1/customer/update-customer",
            "/api/v1/customer/delete-customer",
            "/api/v1/cv/create-cv",
            "/api/v1/cv/update-cv",
            "/api/v1/cv/delete-cv",
            "/api/v1/cv/upload-cv",
            "/api/v1/cv/send-cv-to-email",
            "/api/v1/cv/get-recommendation",
            "/api/v1/cv/get-my-cv",
            "/api/v1/card/add-card",
            "/api/v1/card/update-card/{card_id}",
            "/api/v1/card/delete-card/{card_id}",
            "/api/v1/card/get-cards-by-customer",
    };

    private static final String[] HR_ENDPOINTS = {
            "/api/v1/hr/activate-hr",
            "/api/v1/hr/update-hr",
            "/api/v1/hr/update-hr",
            "/api/v1/hr/update-hr",

    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(CUSTOMER_ENDPOINTS).hasRole("CUSTOMER")
                        .requestMatchers(HR_ENDPOINTS).hasRole("HR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}