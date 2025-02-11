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

}
