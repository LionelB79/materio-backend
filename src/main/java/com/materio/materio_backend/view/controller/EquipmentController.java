package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.BO.EquipmentBO;
import com.materio.materio_backend.business.BO.RoomBO;
import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.Room;
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

    @PostMapping(value ="/equipment")
    public ResponseEntity<String> createEquipment(@RequestBody EquipmentBO equipmentBO) {
        try {
            equipmentService.createEquipments(equipmentBO);
            return ResponseEntity.ok("equipmentBO créé :" + equipmentBO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
