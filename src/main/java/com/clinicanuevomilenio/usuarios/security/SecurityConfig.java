package com.clinicanuevomilenio.usuarios.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final HeaderAuthenticationFilter headerAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // Ya no necesitamos definir reglas de ruta aquí.
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Añadimos nuestro nuevo y simple filtro que lee cabeceras.
                .addFilterBefore(headerAuthFilter, UsernamePasswordAuthenticationFilter.class);


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