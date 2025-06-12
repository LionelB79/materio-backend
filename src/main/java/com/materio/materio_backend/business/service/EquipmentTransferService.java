package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Transfer.EquipmentTransferBO;
import com.materio.materio_backend.dto.Transfer.TransferHistoryVO;

import java.util.List;
import java.util.Set;

public interface EquipmentTransferService {

    Set<TransferHistoryVO> getTransferHistory(Long equipmentId);

    Set<EquipmentTransferBO> processTransfer(EquipmentTransferBO transferBO);
}