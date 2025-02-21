package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.dto.Transfer.TransferRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EquipmentTransferController {

    @Autowired private EquipmentTransferService equipmentTransferService;
    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> transfert(@Valid @RequestBody final TransferRequestDTO transferRequestDTO, @RequestParam final String locality) {

        final List<EquipmentTransfer> equipmentTransfers = equipmentTransferService.processTransfer(locality, transferRequestDTO);
            return ResponseEntity.ok("Transfert effectué : " + equipmentTransfers);
    }
}
