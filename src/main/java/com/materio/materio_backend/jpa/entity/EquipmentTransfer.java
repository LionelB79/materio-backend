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
@Table(name = "t_transfer")
public class EquipmentTransfer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(name = "from_zone_id")
    private Long fromZoneId;

    @Column(name = "to_zone_id")
    private Long toZoneId;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    @Size(max = 1000)
    @Column(name = "transfer_details")
    private String details;
}
