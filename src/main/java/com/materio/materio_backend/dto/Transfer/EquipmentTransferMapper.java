package com.materio.materio_backend.dto.Transfer;


import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EquipmentTransferMapper {

    public EquipmentTransferBO voToBO(EquipmentTransferVO vo) {
        if (vo == null) return null;

        EquipmentTransferBO bo = new EquipmentTransferBO();
        bo.setId(vo.getId());
        bo.setEquipmentIds(vo.getEquipmentIds());
        bo.setTargetZoneId(vo.getTargetZoneId());
        bo.setDetails(vo.getDetails());

        return bo;
    }

    public EquipmentTransferVO boToVO(EquipmentTransferBO bo) {
        if (bo == null) return null;

        EquipmentTransferVO vo = new EquipmentTransferVO();
        vo.setId(bo.getId());
        vo.setEquipmentIds(bo.getEquipmentIds());
        vo.setTargetZoneId(bo.getTargetZoneId());
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

        EquipmentTransferBO bo = new EquipmentTransferBO();
        bo.setId(entity.getId());

        // Ajouter l'ID de l'équipement transféré
        Set<Long> equipmentIds = new HashSet<>();
        equipmentIds.add(entity.getEquipment().getId());
        bo.setEquipmentIds(equipmentIds);

        // Récupérer l'ID de la zone cible
        bo.setTargetZoneId(entity.getToZoneId());
        bo.setTransferDate(entity.getTransferDate());
        bo.setDetails(entity.getDetails());

        return bo;
    }
}
