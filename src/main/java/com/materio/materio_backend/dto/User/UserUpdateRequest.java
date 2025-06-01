package com.materio.materio_backend.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO pour mettre à jour le profil d'un utilisateur
 */
@Data
public class UserUpdateRequest {
    @Size(max = 50, message = "Le prénom ne peut pas dépasser 50 caractères")
    private String firstName;

    @Size(max = 50, message = "Le nom ne peut pas dépasser 50 caractères")
    private String lastName;

    @Size(max = 50, message = "L'email ne peut pas dépasser 50 caractères")
    @Email(message = "L'email doit être valide")
    private String email;

    @Size(min = 6, max = 40, message = "Le mot de passe doit contenir entre 6 et 40 caractères")
    private String password;

    // Champ pour vérification du mot de passe actuel lors du changement
    private String currentPassword;
}
