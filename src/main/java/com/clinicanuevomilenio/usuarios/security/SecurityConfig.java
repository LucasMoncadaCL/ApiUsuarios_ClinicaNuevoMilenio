package com.clinicanuevomilenio.usuarios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar la protección CSRF.
            .csrf(AbstractHttpConfigurer::disable)

            // 2. Configurar las reglas de autorización para las peticiones HTTP.
            .authorizeHttpRequests(auth -> auth
                // Permitir el acceso sin autenticación a cualquier ruta que comience con "/api/usuarios/".
                .requestMatchers("/api/usuarios/**").permitAll()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}