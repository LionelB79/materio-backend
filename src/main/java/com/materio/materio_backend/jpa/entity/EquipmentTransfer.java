package com.materio.materio_backend.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "t_transfer")
public class EquipmentTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    @ToString.Exclude
    private Equipment equipmentId;

    @NotBlank(message = "Le nom de l'equipement transféré est obligatoire")
    @Column(name = "equipment_name")
    private String equipmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_room", referencedColumnName = "name", nullable = false)
    @ToString.Exclude
    private Room fromRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_room", referencedColumnName = "name", nullable = false)
    @ToString.Exclude
    private Room toRoom;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    @Size(max = 1000)
    @Column(name = "transfer_details")
    private String details;
}
