package com.clinicanuevomilenio.usuarios.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rol")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol; 

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre; 

    @Column(name = "requiere_disponibilidad", nullable = false)
    private Boolean requiereDisponibilidad; 

    @Column(name = "descripcion", length = 100)
    private String descripcion; 
}