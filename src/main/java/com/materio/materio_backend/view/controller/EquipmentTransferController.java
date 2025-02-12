package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.EquipmentTransferService;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.view.VO.TransferRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EquipmentTransferController {

    @Autowired private EquipmentTransferService equipmentTransferService;
    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> transfert(@RequestBody TransferRequestVO transferRequestVO) {

            List<EquipmentTransfer> equipmentTransfers = equipmentTransferService.processTransfer(transferRequestVO);
            return ResponseEntity.ok("Transfert effectué : " + equipmentTransfers);


    }
}
