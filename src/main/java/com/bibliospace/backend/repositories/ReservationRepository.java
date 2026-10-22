package com.bibliospace.backend.repositories;

import com.bibliospace.backend.entities.Reservation;
import com.bibliospace.backend.entities.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    // Lista el historial de reservas de un estudiante específico
    List<Reservation> findByUsuarioId(Long usuarioId);
    
    // Útil para verificar qué bloques horarios ya están ocupados en un espacio y fecha
    List<Reservation> findByEspacioIdAndFecha(Long espacioId, LocalDate fecha);
    
    // Para que el Funcionario vea todas las reservas en un estado (Ej: "PENDIENTE")
    List<Reservation> findByEstado(ReservationStatus estado);
}