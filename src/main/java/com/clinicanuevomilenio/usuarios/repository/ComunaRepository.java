package com.clinicanuevomilenio.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicanuevomilenio.usuarios.models.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
}