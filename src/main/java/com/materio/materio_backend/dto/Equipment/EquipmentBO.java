package com.materio.materio_backend.dto.Equipment;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentBO {
    @NotBlank(message = "Le nom de référence est obligatoire")
    private String referenceName;

    @NotBlank(message = "Le numéro de série est obligatoire")
    @Pattern(regexp = "^[A-Za-z0-9-]+$",
            message = "Le numéro de série ne doit contenir que des lettres, des chiffres et des tirets")
    private String serialNumber;

    @NotNull(message = "La date d'achat est obligatoire")
    @Past(message = "La date d'achat doit être dans le passé")
    private LocalDate purchaseDate;

    @Size(max = 100, message = "La marque ne peut pas dépasser 100 caractères")
    private String mark;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    private String roomName;
}
