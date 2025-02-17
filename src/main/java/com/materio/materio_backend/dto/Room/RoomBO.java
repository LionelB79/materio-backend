package com.materio.materio_backend.dto.Room;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomBO {

    @NotBlank(message = "Le nom de la salle est obligatoire")
    private String name;

    @NotBlank(message = "La localité de la salle est obligatoire")
    private String localityName;


    private Set<EquipmentBO> equipments;
}
