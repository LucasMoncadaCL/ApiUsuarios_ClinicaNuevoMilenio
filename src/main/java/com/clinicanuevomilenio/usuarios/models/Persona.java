package com.clinicanuevomilenio.usuarios.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "persona", uniqueConstraints = {
    @UniqueConstraint(columnNames = "rut") 
})
@Data
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Integer idPersona; 

    @Column(name = "rut", nullable = false)
    private Integer rut; 

    @Column(name = "dv", nullable = false, length = 1)
    private Character dv; 

    @Column(name = "pnombre", nullable = false, length = 30)
    private String pnombre; 

    @Column(name = "snombre", length = 30)
    private String snombre; 

    @Column(name = "appaterno", nullable = false, length = 30)
    private String appaterno; 

    @Column(name = "apmaterno", nullable = false, length = 30)
    private String apmaterno; 

    @Column(name = "numero_telefono", nullable = false)
    private Integer numeroTelefono; 

    @Column(name = "especialidad", length = 50)
    private String especialidad; 

    @Column(name = "email", length = 100)
    private String email; 


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_id_usuario", nullable = false, unique = true) //
    private Usuario usuario;

    /* DTO CrearUsuarioRequest pide un `comunaId`.
       La tabla PERSONA no tiene una relación directa con COMUNA.
       la relación es: SEDE -> COMUNA.
       PABELLON -> SEDE.
    */
}