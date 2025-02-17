package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Equipment.EquipmentVO;
import com.materio.materio_backend.jpa.entity.Equipment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @PostMapping(value = "/equipment")
    public ResponseEntity<String> createEquipment(@Valid @RequestBody EquipmentBO equipmentBO) {

        Equipment newEquipment = equipmentService.createEquipment(equipmentBO);
        EquipmentVO response = equipmentMapper.EntityToVO(newEquipment);
        return ResponseEntity.ok("equipmentBO créé :" + response);

    }

    @DeleteMapping("/equipment/{referenceName}/{serialNumber}")
    public ResponseEntity<String> deleteEquipment(
            @PathVariable String referenceName,
            @PathVariable String serialNumber) {

        equipmentService.deleteEquipment(serialNumber, referenceName);
        return ResponseEntity.ok("equipment supprimé avec succès :" + referenceName);
    }
}
