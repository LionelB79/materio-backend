package com.materio.materio_backend.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_equipment")
public class Equipment extends BaseEntity {

 @EmbeddedId
 private EquipmentPK id = new EquipmentPK();

 @NotNull(message = "La date d'achat est obligatoire")
 @Past(message = "La date d'achat doit être dans le passé")
 @Column(name = "purchase_date", nullable = false)
 private LocalDate purchaseDate;

 @Size(max = 100)
 @Column(name = "mark")
 private String mark;

 @Size(max = 500)
 @Column(name = "description")
 private String description;

 @JsonBackReference("zone-equipments")
 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 @JoinColumn(name = "zone_id", nullable = false)
 private Zone zone;

 @Column(name = "tag")
 @Size(max = 100, message = "Le tag ne peut pas dépasser 100 caractères")
 private String tag;

 @Column(name = "barcode")
 private Integer barcode;

 @Transient
 public String getSerialNumber() {
  return id.getSerialNumber();
 }

 public void setSerialNumber(String serialNumber) {
  id.setSerialNumber(serialNumber);
 }

 @Transient
 public String getReferenceName() {
  return id.getReferenceName();
 }

 public void setReferenceName(String referenceName) {
  id.setReferenceName(referenceName);
 }
}
