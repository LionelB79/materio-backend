package com.materio.materio_backend.dto.Locality;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalityBO {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String name;

    @NotNull(message = "Le code postal est obligatoire")
    @Min(value = 1000, message = "Le code postal doit être valide")
    @Max(value = 99999, message = "Le code postal doit être valide")
    private int cp;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 100, message = "L'adresse ne peut pas dépasser 100 caractères")
    private String address;

    @NotBlank(message = "La ville est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la ville doit contenir entre 2 et 100 caractères")
    private String city;
}
