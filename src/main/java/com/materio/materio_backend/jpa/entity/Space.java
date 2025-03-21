package com.materio.materio_backend.jpa.entity;

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
@Table(name = "t_space",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "locality_id"}))
public class Space extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'espace est obligatoire")
    @Size(min = 2, max = 25, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonManagedReference("space-zones")
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Zone> zones = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locality_id", nullable = false)
    @JsonIgnoreProperties("spaces")
    private Locality locality;
}
