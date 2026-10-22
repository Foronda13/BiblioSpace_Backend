package com.bibliospace.backend.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Integer capacidad;
    private String estado;
}