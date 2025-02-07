package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "t_room")
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Equipment> equipements;

}
