package com.materio.materio_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_reference")
public class EquipementRef extends BaseEntity {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "reference", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Equipment> equipments = new ArrayList<>();
}
