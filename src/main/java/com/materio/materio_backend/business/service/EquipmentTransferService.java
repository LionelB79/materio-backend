package com.materio.materio_backend.business.service;

import com.materio.materio_backend.business.BO.EquipmentBO;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.view.VO.TransferRequestVO;

import java.util.List;

public interface EquipmentTransferService {
    public List<EquipmentTransfer> processTransfer(TransferRequestVO transferRequest);
}
