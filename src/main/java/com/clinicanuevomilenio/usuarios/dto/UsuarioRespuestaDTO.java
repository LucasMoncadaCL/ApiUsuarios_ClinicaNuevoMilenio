package com.clinicanuevomilenio.usuarios.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class UsuarioRespuestaDTO {
    private Integer idUsuario;
    private String username;
    private String nombreCompleto;
    private String email;
    private String rol;
    private Boolean estado;
    private LocalDate fechaRegistro;
}