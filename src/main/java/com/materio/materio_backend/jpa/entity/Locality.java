package com.materio.materio_backend.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_locality")
public class Locality extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le name est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(name = "name",unique = true)
    private String name;

    @NotBlank(message = "L'adress est obligatoire")
    @Size(max = 100, message = "L'adresse ne peut pas dépasser 100 caractères")
    @Column(name="adress")
    private String address;

    @NotNull(message = "Le code postal est obligatoire")
    @Min(value = 1000, message = "Le code postal doit être valide")
    @Max(value = 99999, message = "Le code postal doit être valide")
    @Column(name = "cp")
    private Integer cp;

    @NotBlank(message = "La ville est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la ville doit contenir entre 2 et 100 caractères")
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "locality", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Room> rooms = new HashSet<>();
}
