package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferBO;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferMapper;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferVO;
import com.materio.materio_backend.dto.Transfer.TransferHistoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/transfers")
public class EquipmentTransferController {

    @Autowired
    private EquipmentTransferService transferService;

    @Autowired
    private EquipmentTransferMapper transferMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<EquipmentTransferVO>> transferEquipments(
            @Valid @RequestBody EquipmentTransferVO transferVO) {
        EquipmentTransferBO transferBO = transferMapper.voToBO(transferVO);
        Set<EquipmentTransferBO> resultBOs = transferService.processTransfer(transferBO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transferMapper.boSetToVOSet(resultBOs));
    }

    @GetMapping("/equipment/{equipmentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Set<TransferHistoryVO>> getTransferHistory(
            @PathVariable Long equipmentId) {
        Set<TransferHistoryVO> history = transferService.getTransferHistory(equipmentId);
        return ResponseEntity.ok(history);
    }
}
