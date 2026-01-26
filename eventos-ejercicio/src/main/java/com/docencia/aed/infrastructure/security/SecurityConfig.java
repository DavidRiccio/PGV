package com.docencia.aed.infrastructure.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(AppSecurityProperties.class)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Para el ejercicio: passwords en claro. En producción usar
        // BCryptPasswordEncoder.
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(AppSecurityProperties props) {
        InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
        for (var u : props.getUsers()) {
            String[] roles = u.getRoles().toArray(new String[0]);
            mgr.createUser(User.withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(roles)
                    .build());
        }
        return mgr;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Endpoints Públicos
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/v1/events/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**")
                        .permitAll()
                        .requestMatchers("/api/v2/events/**").hasAnyRole("ADMIN","COLLABORATOR")
                        .requestMatchers(HttpMethod.DELETE,"/api/v2/events/**").hasRole("ADMIN")
                        .requestMatchers("/api/v2/events/approve").hasRole("ADMIN")
                        .requestMatchers("/api/v2/events/reject").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> httpBasic.disable());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}