package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_equipment")
public class Equipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reference", nullable = false)
    private EquipementRef reference;

    @Column(name = "mark", nullable = true)
    private String mark;

    @Column(name = "description",nullable = true)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room", nullable = false)
    private Room room;
}
