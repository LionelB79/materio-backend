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
}
