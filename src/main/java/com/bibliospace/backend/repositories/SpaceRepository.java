package com.bibliospace.backend.repositories;

import com.bibliospace.backend.entities.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    
    // Encuentra todos los espacios por su tipo (Ej: "SALA_ESTUDIO")
    List<Space> findByTipo(String tipo);
    
    // Encuentra todos los espacios por su estado (Ej: "ACTIVO")
    List<Space> findByEstado(String estado);
}