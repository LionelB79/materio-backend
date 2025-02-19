package com.materio.materio_backend.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_zone")
public class Zone extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la zone est obligatoire")
    @Size(min = 2, max = 25, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "space_id", nullable = false)
    @JsonBackReference("space-zones")
    private Space space;

    @JsonManagedReference("zone-equipments")
    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY)
    private Set<Equipment> equipments = new HashSet<>();
}
