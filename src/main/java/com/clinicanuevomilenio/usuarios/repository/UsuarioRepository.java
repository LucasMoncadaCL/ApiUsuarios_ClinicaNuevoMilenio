package com.clinicanuevomilenio.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinicanuevomilenio.usuarios.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
