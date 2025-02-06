package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="room_name" , nullable = false, unique = true)
    private String roomName;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Equipment> equipements;
}
