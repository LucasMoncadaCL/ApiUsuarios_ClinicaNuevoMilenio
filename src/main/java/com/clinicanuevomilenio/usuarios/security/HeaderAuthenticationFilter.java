package com.clinicanuevomilenio.usuarios.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Lee las cabeceras que el Gateway debió haber añadido.
        final String username = request.getHeader("X-User-Username");
        final String rolesHeader = request.getHeader("X-User-Roles");

        // 2. Si las cabeceras existen, confiamos en ellas y establecemos la autenticación.
        if (username != null && rolesHeader != null) {

            // Convertimos el string de roles (ej. "ROLE_ADMINISTRATIVO,ROLE_USER") en una lista de autoridades.
            List<GrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Creamos el objeto de autenticación. No necesitamos contraseña aquí.
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);

            // Establecemos el usuario como autenticado en el contexto de seguridad de Spring.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 3. Pasamos la petición al siguiente filtro de la cadena.
        filterChain.doFilter(request, response);
    }
}