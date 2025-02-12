package com.materio.materio_backend.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_reference")
public class EquipmentRef extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    @Column(name = "name", unique = true)
    private String name;

    @NotBlank(message = "La quantité d'objets à créer est obligatoire")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;


}
