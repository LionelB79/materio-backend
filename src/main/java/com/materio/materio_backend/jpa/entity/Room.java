package com.materio.materio_backend.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_room")
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    @Size(min = 2, max = 25, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonManagedReference("room-equipments")
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private Set<Equipment> equipments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locality_id", nullable = false)
    @JsonIgnoreProperties("rooms")
    private Locality locality;
}
