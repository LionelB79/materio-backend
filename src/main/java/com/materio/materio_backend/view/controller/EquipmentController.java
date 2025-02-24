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

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @PostMapping(value = "/equipment")
    public ResponseEntity<String> createEquipment(@Valid @RequestBody EquipmentBO equipmentBO) {

        final EquipmentBO request = equipmentService.createEquipment(equipmentBO);
        final EquipmentVO response = equipmentMapper.boToVO(request);
        return ResponseEntity.ok("equipmentBO créé :" + response);

    }

    @DeleteMapping("/equipment/{referenceName}/{serialNumber}")
    public ResponseEntity<String> deleteEquipment(
            @PathVariable final String referenceName,
            @PathVariable final String serialNumber) {

        equipmentService.deleteEquipment(serialNumber, referenceName);
        return ResponseEntity.ok("equipment supprimé avec succès :" + referenceName);
    }
    @PutMapping("/equipment/{referenceName}/{serialNumber}")
    public ResponseEntity<EquipmentVO> updateEquipment(
            @PathVariable String referenceName,
            @PathVariable String serialNumber,
            @Valid @RequestBody EquipmentVO equipmentVO) {

        if (!referenceName.equals(equipmentVO.getReferenceName())
                || !serialNumber.equals(equipmentVO.getSerialNumber())) {
            return ResponseEntity.badRequest().build();
        }

        EquipmentBO equipmentBO = equipmentMapper.voToBO(equipmentVO);
        EquipmentBO updatedEquipment = equipmentService.updateEquipment(equipmentBO);

        return ResponseEntity.ok(equipmentMapper.boToVO(updatedEquipment));
    }
    @GetMapping("/equipment/{referenceName}/{serialNumber}")
    public ResponseEntity<EquipmentVO> getEquipment(
            @PathVariable String referenceName,
            @PathVariable String serialNumber) {

        EquipmentBO equipment = equipmentService.getEquipment(serialNumber, referenceName);
        return ResponseEntity.ok(equipmentMapper.boToVO(equipment));
    }

    @GetMapping("/equipments/zone")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsByZone(
            @RequestParam String localityName,
            @RequestParam String spaceName,
            @RequestParam String zoneName) {

        Set<EquipmentBO> equipments = equipmentService.getEquipmentsByZone(
                localityName, spaceName, zoneName);

        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipments/space")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsBySpace(
            @RequestParam String localityName,
            @RequestParam String spaceName) {

        Set<EquipmentBO> equipments = equipmentService.getEquipmentsBySpace(
                localityName, spaceName);

        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipments/locality/{localityName}")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsByLocality(
            @PathVariable String localityName) {

        Set<EquipmentBO> equipments = equipmentService.getEquipmentsByLocality(localityName);
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipments")
    public ResponseEntity<Set<EquipmentVO>> getAllEquipments() {
        Set<EquipmentBO> equipments = equipmentService.getAllEquipments();
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }
}
