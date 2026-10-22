package com.bibliospace.backend.services;

import com.bibliospace.backend.dtos.request.SpaceRequest;
import com.bibliospace.backend.dtos.response.SpaceResponse;
import com.bibliospace.backend.entities.Space;
import com.bibliospace.backend.repositories.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;

    public List<SpaceResponse> getAllSpaces() {
        return spaceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SpaceResponse getSpaceById(Long id) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        return mapToResponse(space);
    }

    public SpaceResponse createSpace(SpaceRequest request) {
        Space space = Space.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .tipo(request.getTipo())
                .capacidad(request.getCapacidad())
                .estado(request.getEstado())
                .build();

        Space savedSpace = spaceRepository.save(space);
        return mapToResponse(savedSpace);
    }

    private SpaceResponse mapToResponse(Space space) {
        return SpaceResponse.builder()
                .id(space.getId())
                .nombre(space.getNombre())
                .descripcion(space.getDescripcion())
                .tipo(space.getTipo())
                .capacidad(space.getCapacidad())
                .estado(space.getEstado())
                .build();
    }
}