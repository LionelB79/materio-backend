package com.materio.materio_backend.dto.Zone;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneVO {
    @NotBlank(message = "Le nom de la zone est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom de la zone doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-_]+$", message = "Le nom de la zone ne doit contenir que des lettres, chiffres, espaces, tirets et underscores")
    private String name;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @NotBlank(message = "Le nom de l'espace est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom de l'espace doit contenir entre 2 et 50 caractères")
    private String spaceName;

    @NotBlank(message = "Le nom de la localité est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom de la localité doit contenir entre 2 et 50 caractères")
    private String localityName;

    @NotBlank(message = "Le nom de la zone est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom de la zone doit contenir entre 2 et 50 caractères")
    private String zoneName;

    @Valid
    private Set<EquipmentBO> equipments = new HashSet<>();
}
