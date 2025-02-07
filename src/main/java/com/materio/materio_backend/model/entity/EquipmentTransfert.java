package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "t_transfer")
public class EquipmentTransfert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "from_room", nullable = false)
    private Room fromRoom;

    @ManyToOne
    @JoinColumn(name = "to_room", nullable = false)
    private Room toRoom;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    @Column(name = "transfer_reason")
    private String reason;
}
