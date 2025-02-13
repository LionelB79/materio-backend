package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Equipment.EquipmentVO;
import com.materio.materio_backend.jpa.entity.Equipment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EquipmentController {
@Autowired private EquipmentService equipmentService;
@Autowired private EquipmentMapper equipmentMapper;



    @PostMapping(value ="/equipment")
    public ResponseEntity<String> createEquipment(@Valid @RequestBody EquipmentBO equipmentBO) {

        Equipment equipment = equipmentMapper.BOToEntity(equipmentBO);
            Equipment newEquipment = equipmentService.createEquipment(equipment);
        EquipmentVO response = equipmentMapper.EntityToVO(newEquipment);
            return ResponseEntity.ok("equipmentBO créé :" + response);

    }
}
