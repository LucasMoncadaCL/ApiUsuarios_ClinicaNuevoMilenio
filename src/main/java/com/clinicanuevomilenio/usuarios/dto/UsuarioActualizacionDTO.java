package com.clinicanuevomilenio.usuarios.dto;

import lombok.Data;

@Data
public class UsuarioActualizacionDTO {
    private String pnombre;
    private String snombre;
    private String appaterno;
    private String apmaterno;
    private Integer numeroTelefono;
    private String email;
    private String especialidad;
    private Integer rolId;
    private Boolean estado;
}