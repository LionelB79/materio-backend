package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_room")
public class Room extends BaseEntity {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Equipment> equipements;

}
