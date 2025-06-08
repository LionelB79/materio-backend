package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentService;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Equipment.EquipmentVO;
import com.materio.materio_backend.jpa.entity.Equipment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @PostMapping(value = "/equipment", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<EquipmentVO> createEquipment(@Valid @RequestBody EquipmentBO equipmentBO) {
        final EquipmentBO created = equipmentService.createEquipment(equipmentBO);
        final EquipmentVO response = equipmentMapper.boToVO(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/equipment/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<EquipmentVO> getEquipment(@PathVariable Long id) {
        EquipmentBO equipment = equipmentService.getEquipment(id);
        return ResponseEntity.ok(equipmentMapper.boToVO(equipment));
    }

    @PutMapping("/equipment/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<EquipmentVO> updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentVO equipmentVO) {
        EquipmentBO equipmentBO = equipmentMapper.voToBO(equipmentVO);
        EquipmentBO updatedEquipment = equipmentService.updateEquipment(id, equipmentBO);
        return ResponseEntity.ok(equipmentMapper.boToVO(updatedEquipment));
    }

    @DeleteMapping("/equipment/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }

    // Nouvelles méthodes utilisant les IDs
    @GetMapping("/equipment/zone/{zoneId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsByZoneId(@PathVariable Long zoneId) {
        Set<EquipmentBO> equipments = equipmentService.getEquipmentsByZoneId(zoneId);
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipment/space/{spaceId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsBySpaceId(@PathVariable Long spaceId) {
        Set<EquipmentBO> equipments = equipmentService.getEquipmentsBySpaceId(spaceId);
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/equipment/locality/{localityId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsByLocalityId(@PathVariable Long localityId) {
        Set<EquipmentBO> equipments = equipmentService.getEquipmentsByLocalityId(localityId);
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    // Méthodes de compatibilité (à déprécier dans le futur)
    @Deprecated
    @GetMapping("/equipment/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<EquipmentVO> getEquipmentBySerialAndReference(
            @RequestParam String referenceName,
            @RequestParam String serialNumber) {
        EquipmentBO equipment = equipmentService.getEquipmentBySerialAndReference(serialNumber, referenceName);
        return ResponseEntity.ok(equipmentMapper.boToVO(equipment));
    }

    @Deprecated
    @DeleteMapping("/equipment/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEquipmentBySerialAndReference(
            @RequestParam String referenceName,
            @RequestParam String serialNumber) {
        equipmentService.deleteEquipmentBySerialAndReference(serialNumber, referenceName);
        return ResponseEntity.noContent().build();
    }

    @Deprecated
    @GetMapping("/equipment/zone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @Deprecated
    @GetMapping("/{localityName}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<EquipmentVO>> getEquipmentsByLocality(
            @PathVariable String localityName) {
        Set<EquipmentBO> equipments = equipmentService.getEquipmentsByLocality(localityName);
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<EquipmentVO>> getAllEquipments() {
        Set<EquipmentBO> equipments = equipmentService.getAllEquipments();
        Set<EquipmentVO> response = equipments.stream()
                .map(equipmentMapper::boToVO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }
}