package com.materio.materio_backend.business.service;

import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.dto.Transfer.TransferRequestDTO;

import java.util.List;

public interface EquipmentTransferService {
    public List<EquipmentTransfer> processTransfer(TransferRequestDTO transferRequest);
}
