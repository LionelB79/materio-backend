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
            @JoinColumn(name = "equipment_reference_name",
                    referencedColumnName = "equipment_reference_name"),
            @JoinColumn(name = "equipment_serial_number",
                    referencedColumnName = "equipment_serial_number")
    })
    private Equipment equipment;

    @Column(name = "from_zone")
    private String fromZone;

    @Column(name = "to_zone")
    private String toZone;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    @Size(max = 1000)
    @Column(name = "transfer_details")
    private String details;

    @Column(name = "from_locality")
    private String fromLocality;

    @Column(name = "to_locality")
    private String toLocality;
}
