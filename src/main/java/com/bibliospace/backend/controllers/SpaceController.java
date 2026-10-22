package com.bibliospace.backend.controllers;

import com.bibliospace.backend.dtos.request.SpaceRequest;
import com.bibliospace.backend.dtos.response.SpaceResponse;
import com.bibliospace.backend.services.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// Importante: Usamos PreAuthorize para proteger rutas según el rol
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spaces")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    // Cualquier usuario autenticado (ESTUDIANTE, FUNCIONARIO, ADMIN) puede ver el catálogo
    @GetMapping
    public ResponseEntity<List<SpaceResponse>> getAllSpaces() {
        return ResponseEntity.ok(spaceService.getAllSpaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponse> getSpaceById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(spaceService.getSpaceById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // SOLO un ADMIN puede crear nuevos espacios
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SpaceResponse> createSpace(@Valid @RequestBody SpaceRequest request) {
        return ResponseEntity.ok(spaceService.createSpace(request));
    }
}