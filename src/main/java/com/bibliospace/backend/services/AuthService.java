package com.bibliospace.backend.services;

import com.bibliospace.backend.dtos.request.LoginRequest;
import com.bibliospace.backend.dtos.request.RegisterRequest;
import com.bibliospace.backend.dtos.response.AuthResponse;
import com.bibliospace.backend.entities.Role;
import com.bibliospace.backend.entities.User;
import com.bibliospace.backend.repositories.UserRepository;
import com.bibliospace.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        // Por defecto, los registros desde el frontend serán de ESTUDIANTE
        User user = User.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Role.ROLE_ESTUDIANTE)
                .build();

        userRepository.save(user);

        // Hacemos login automático después del registro
        return login(new LoginRequest(request.getEmail(), request.getPassword()));
    }

    public AuthResponse login(LoginRequest request) {
        // Autentica las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genera el token
        String token = jwtTokenProvider.generateToken(authentication);

        // Busca al usuario para devolver sus datos en la respuesta
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        return AuthResponse.builder()
                .token(token)
                .nombre(user.getNombre())
                .email(user.getEmail())
                .rol(user.getRol().name())
                .build();
    }
}