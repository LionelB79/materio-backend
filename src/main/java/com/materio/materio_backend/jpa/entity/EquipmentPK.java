package com.materio.materio_backend.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "equipment_reference_name", nullable = false)
    private String referenceName;

    @Column(name = "equipment_serial_number", nullable = false, length = 100)
    private String serialNumber;


}
