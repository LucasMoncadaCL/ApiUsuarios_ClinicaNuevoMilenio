package com.clinicanuevomilenio.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinicanuevomilenio.usuarios.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByusername(String username);
    boolean existsByusername(String username);
    List<Usuario> findByRol_NombreIgnoreCase(String nombreRol);
}
