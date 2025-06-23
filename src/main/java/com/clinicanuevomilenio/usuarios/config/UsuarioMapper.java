package com.clinicanuevomilenio.usuarios.config;

import org.springframework.stereotype.Component;

import com.clinicanuevomilenio.usuarios.dto.UsuarioRespuestaDTO;
import com.clinicanuevomilenio.usuarios.models.Persona;
import com.clinicanuevomilenio.usuarios.models.Usuario;

@Component // Marca esta clase como un componente de Spring para que pueda ser inyectado.
public class UsuarioMapper {

    public UsuarioRespuestaDTO toUsuarioRespuestaDTO(Usuario usuario) {
        Persona persona = usuario.getPersona();
        if (persona == null) {
            // Manejo de caso borde por si la persona no estuviera cargada
            throw new IllegalStateException("El usuario con ID " + usuario.getIdUsuario() + " no tiene una persona asociada.");
        }

        String nombreCompleto = persona.getPnombre() + " " + persona.getAppaterno();

        // DTO de respuesta usando el patrón Builder
        return UsuarioRespuestaDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .nombreCompleto(nombreCompleto)
                .email(persona.getEmail())
                .rol(usuario.getRol().getNombre())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}