package com.materio.materio_backend.dto.Transfer;


import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentTransferMapper {

    public EquipmentTransferBO voToBO(EquipmentTransferVO vo) {
        if (vo == null) return null;

        EquipmentTransferBO bo = new EquipmentTransferBO();
        bo.setEquipments(vo.getEquipments());  // La liste d'EquipmentToTransfer
        bo.setTargetZoneName(vo.getTargetZoneName());
        bo.setTargetSpaceName(vo.getTargetSpaceName());
        bo.setTargetLocalityName(vo.getTargetLocalityName());
        bo.setDetails(vo.getDetails());

        return bo;
    }

    public EquipmentTransferVO boToVO(EquipmentTransferBO bo) {
        if (bo == null) return null;

        EquipmentTransferVO vo = new EquipmentTransferVO();
        vo.setEquipments(bo.getEquipments());  // La liste d'EquipmentToTransfer
        vo.setTargetZoneName(bo.getTargetZoneName());
        vo.setTargetSpaceName(bo.getTargetSpaceName());
        vo.setTargetLocalityName(bo.getTargetLocalityName());
        vo.setDetails(bo.getDetails());

        return vo;
    }

    public Set<EquipmentTransferVO> boSetToVOSet(Set<EquipmentTransferBO> bos) {
        if (bos == null) return null;
        return bos.stream()
                .map(this::boToVO)
                .collect(Collectors.toSet());
    }

    public EquipmentTransferBO entityToBO(EquipmentTransfer entity) {
        if (entity == null) return null;

        // Création de l'EquipmentToTransfer pour la source
        EquipmentToTransfer sourceEquipment = new EquipmentToTransfer();
        sourceEquipment.setReferenceName(entity.getEquipment().getReferenceName());
        sourceEquipment.setSerialNumber(entity.getEquipment().getSerialNumber());
        sourceEquipment.setSourceZoneName(entity.getFromZone());
        sourceEquipment.setSourceSpaceName(entity.getFromSpace());

        // Création du BO
        EquipmentTransferBO bo = new EquipmentTransferBO();
        bo.setEquipments(Set.of(sourceEquipment));
        bo.setTargetZoneName(entity.getToZone());
        bo.setTargetSpaceName(entity.getToSpace());
        bo.setTargetLocalityName(entity.getToLocality());
        bo.setTransferDate(entity.getTransferDate());
        bo.setDetails(entity.getDetails());

        return bo;
    }
}