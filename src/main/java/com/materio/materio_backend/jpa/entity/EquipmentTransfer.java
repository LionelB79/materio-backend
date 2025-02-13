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
    @JoinColumns({
            // L'ordre des colonnes doit correspondre à l'ordre dans EquipmentPK
            @JoinColumn(name = "equipment_reference_name",
                    referencedColumnName = "equipment_reference_name"),
            @JoinColumn(name = "equipment_serial_number",
                    referencedColumnName = "equipment_serial_number")
    })
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_room", referencedColumnName = "name")
    private Room fromRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_room", referencedColumnName = "name")
    private Room toRoom;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    @Size(max = 1000)
    @Column(name = "transfer_details")
    private String details;


}
