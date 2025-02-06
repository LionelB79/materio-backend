package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipement_ref")
    private String equipmentRef;

    @Column(name = "equipement_mark", nullable = true)
    private String equipmentMark;

    @Column(name = "equipement_description",nullable = true)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
