package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferBO;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferMapper;
import com.materio.materio_backend.dto.Transfer.EquipmentTransferVO;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class EquipmentTransferController {
@Autowired
    private EquipmentTransferService transferService;
@Autowired
    private EquipmentTransferMapper transferMapper;

    @PostMapping
    public ResponseEntity<EquipmentTransferVO> transferEquipment(
            @Valid @RequestBody EquipmentTransferVO transferVO) {

        // Conversion en BO et traitement
        EquipmentTransferBO transferBO = transferMapper.voToBO(transferVO);
        EquipmentTransferBO resultBO = transferService.processTransfer(transferBO);

        // Conversion du résultat en VO
        return ResponseEntity.ok(transferMapper.boToVO(resultBO));
    }

    @GetMapping("/{referenceName}/{serialNumber}")
    public ResponseEntity<List<EquipmentTransferVO>> getTransferHistory(
            @PathVariable String referenceName,
            @PathVariable String serialNumber) {

        List<EquipmentTransferBO> historyBO = transferService.getTransferHistory(referenceName, serialNumber);
        return ResponseEntity.ok(transferMapper.boListToVOList(historyBO));
    }
}
