package com.materio.materio_backend.jpa.entity;

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
    @JoinColumn(name = "equipment_id",referencedColumnName = "id", nullable = false)
    private Equipment equipmentId;

    @ManyToOne
    @JoinColumn(name = "equipment_name", referencedColumnName = "reference", nullable = false)
    private Equipment equipmentName;

    @ManyToOne
    @JoinColumn(name = "from_room", referencedColumnName = "name", nullable = false)
    private Room fromRoom;

    @ManyToOne
    @JoinColumn(name = "to_room", referencedColumnName = "name", nullable = false)
    private Room toRoom;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    @Column(name = "transfer_reason")
    private String reason;
}
