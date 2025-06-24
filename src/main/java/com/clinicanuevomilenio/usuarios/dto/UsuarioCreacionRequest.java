package com.clinicanuevomilenio.usuarios.dto;

import lombok.Data;

@Data
public class UsuarioCreacionRequest {

    // Datos para la entidad Usuario
    private String username;
    private String password;
    private Integer rolId;

    // Datos para la entidad Persona
    private Integer rut;
    private Character dv;
    private String pnombre;
    private String snombre;
    private String appaterno;
    private String apmaterno;
    private Integer numeroTelefono;
    private String especialidad;
    private String email;
}