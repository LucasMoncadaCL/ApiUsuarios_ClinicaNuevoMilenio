package com.clinicanuevomilenio.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicanuevomilenio.usuarios.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}