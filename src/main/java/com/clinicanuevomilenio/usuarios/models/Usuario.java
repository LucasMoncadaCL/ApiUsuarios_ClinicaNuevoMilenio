package com.clinicanuevomilenio.usuarios.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "usuario", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username") 
})
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario; 

    @Column(name = "username", nullable = false, length = 20)
    private String username; 

    @Column(name = "password", nullable = false, length = 255)
    private String password; 

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro; 

    @Column(name = "estado", nullable = false)
    private Boolean estado; 

    @Column(name = "esta_disponible", nullable = false)
    private Boolean estaDisponible; 

    @Column(name = "ultima_actualizacion_disponibilidad")
    private LocalDate ultimaActualizacionDisponibilidad; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROL_id_rol", nullable = false) 
    private Rol rol;

    // Relación Inversa: Un Usuario tiene una Persona asociada.
    // 'mappedBy' indica que la relación la gestiona la entidad Persona.
    // 'cascade = CascadeType.ALL' hace que si se guarda/elimina un usuario, también se afecta a su persona.
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Persona persona;
}