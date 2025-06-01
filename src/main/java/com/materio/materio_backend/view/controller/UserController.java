package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.UserService;
import com.materio.materio_backend.dto.User.UserProfileResponse;
import com.materio.materio_backend.dto.User.UserUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour gérer les opérations sur les utilisateurs
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Récupère le profil de l'utilisateur actuellement connecté
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> getCurrentUserProfile() {
        UserProfileResponse profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }

    /**
     * Met à jour le profil de l'utilisateur actuellement connecté
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile(@Valid @RequestBody UserUpdateRequest updateRequest) {
        UserProfileResponse updatedProfile = userService.updateCurrentUserProfile(updateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Récupère un utilisateur par son ID (admin seulement)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        UserProfileResponse profile = userService.getUserProfileById(id);
        return ResponseEntity.ok(profile);
    }

    /**
     * Supprime un utilisateur par son ID (admin seulement)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
