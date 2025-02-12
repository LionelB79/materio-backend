package com.materio.materio_backend.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_equipment")
public class Equipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "reference_name", referencedColumnName="name",nullable = false)
    private String referenceName;

    @Column(name = "mark", nullable = true)
    private String mark;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_name", referencedColumnName = "name", nullable = false)
    private Room room;

}
