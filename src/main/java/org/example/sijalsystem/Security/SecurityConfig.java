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
            "/api/v1/hr/get-hr-ordered",
            "/api/v1/interview-session/get_question/**",
            "/health",
            "/api/v1/vapi/webhook",
            "/api/v1/rating-hr/get-rating-by-hr/**",
            "/api/v1/rating-hr/get-top-rating",
            "/api/v1/payments/callback"


    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/v1/cv/get-all-cv",
            "/api/v1/card/get-cards",
            "/api/v1/customer/get-customers",
            "/api/v1/hr/activate-hr/**",
            "/api/v1/Interview-analysis-by-hr/get-interviews-analysis",
            "/api/v1/rating-hr/get-rating",
            "/api/v1/request-interview/get-Request",
            "/api/v1/subscription/get-subscription",
            "/api/v1/Interview-analysis-by-hr/get-interviews-analysis"
    };

    private static final String[] CUSTOMER_ENDPOINTS = {
            "/api/v1/customer/update-customer",
            "/api/v1/customer/delete-customer",
            "/api/v1/cv/create-cv",
            "/api/v1/cv/update-cv",
            "/api/v1/cv/delete-cv",
            "/api/v1/cv/upload-cv",
            "/api/v1/cv/download-generate-cv",
            "/api/v1/cv/send-cv-to-email",
            "/api/v1/cv/get-recommendation",
            "/api/v1/cv/get-my-cv",
            "/api/v1/card/add-card",
            "/api/v1/card/update-card/**",
            "/api/v1/card/delete-card/**",
            "/api/v1/card/get-my-cards",
            "/api/v1/analysis-by-ai/all-analysis",
            "/api/v1/analysis-by-ai/analysis-for-session/**",
            "/api/v1/interview-session/start-session-with-cv",
            "/api/v1/interview-session/start-session-with-description",
            "/api/v1/interview-session/get-my-sessions",
            "/api/v1/interview-session/get-session-by-id/**",
            "/api/v1/rating-hr/add-rating/**",
            "/api/v1/rating-hr/update-rating/**",
            "/api/v1/rating-hr/delete-rating/**",
            "/api/v1/rating-hr/get-rating-by-customer",
            "/api/v1/request-interview/send-request/**",
            "/api/v1/request-interview/update-request/**",
            "/api/v1/request-interview/delete-request/**",
            "/api/v1/subscription/subscribe",
            "/api/v1/subscription/cancel-subscribe/**",
            "/api/v1/Interview-with-hr/get-interview-by-customer",
            "/api/v1/Interview-analysis-by-hr/development-plan"

    };

    private static final String[] HR_ENDPOINTS = {
            "/api/v1/hr/delete-hr",
            "/api/v1/hr/update-hr",
            "/api/v1/hr/update-hr",
            "/api/v1/Interview-analysis-by-hr/add-interview-analysis/**",
            "/api/v1/Interview-analysis-by-hr/update-interview-analysis/**",
            "/api/v1/Interview-analysis-by-hr/delete-interview-analysis/**",
            "/api/v1/request-interview/approve-request/**",
            "/api/v1/request-interview/reject-request/**",
            "/api/v1/Interview-with-hr/start-interview/**",
            "/api/v1/Interview-with-hr/end-interview/**",
            "/api/v1/Interview-with-hr/cancel-interview/**",
            "/api/v1/Interview-with-hr/get-interview-by-hr"

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