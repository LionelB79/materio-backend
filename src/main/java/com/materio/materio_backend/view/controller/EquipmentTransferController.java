package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferBO;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferMapper;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/transfers")
public class EquipmentTransferController {
@Autowired
    private EquipmentTransferService transferService;
@Autowired
    private EquipmentTransferMapper transferMapper;

    @PostMapping
    public ResponseEntity<Set<EquipmentTransferVO>> transferEquipments(
            @Valid @RequestBody EquipmentTransferVO transferVO) {
        EquipmentTransferBO transferBO = transferMapper.voToBO(transferVO);
        Set<EquipmentTransferBO> resultBOs = transferService.processTransfer(transferBO);
        return ResponseEntity.ok(transferMapper.boSetToVOSet(resultBOs));
    }

    @GetMapping("/{referenceName}/{serialNumber}")
    public ResponseEntity<Set<EquipmentTransferVO>> getTransferHistory(
            @PathVariable String referenceName,
            @PathVariable String serialNumber) {

        Set<EquipmentTransferBO> historyBO = transferService.getTransferHistory(referenceName, serialNumber);
        return ResponseEntity.ok(transferMapper.boSetToVOSet(historyBO));
    }
}
