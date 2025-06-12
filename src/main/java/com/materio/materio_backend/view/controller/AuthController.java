package com.materio.materio_backend.view.controller;


import com.materio.materio_backend.business.service.impl.AuthServiceImpl;
import com.materio.materio_backend.dto.User.JwtResponse;
import com.materio.materio_backend.dto.User.LoginRequest;
import com.materio.materio_backend.dto.User.MessageResponse;
import com.materio.materio_backend.dto.User.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour gérer les requêtes d'authentification
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    /**
     * Authentifie un utilisateur et génère un token JWT
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authServiceImpl.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Inscrit un nouvel utilisateur
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authServiceImpl.registerUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: " + e.getMessage()));
        }
    }
}
