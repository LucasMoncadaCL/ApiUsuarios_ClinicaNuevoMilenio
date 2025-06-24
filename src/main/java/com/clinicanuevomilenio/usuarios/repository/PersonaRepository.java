package com.clinicanuevomilenio.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinicanuevomilenio.usuarios.models.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{

}
