package com.materio.materio_backend.dto.Transfer;


import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentTransferMapper {

    public EquipmentTransferBO voToBO(EquipmentTransferVO vo) {
        if (vo == null) return null;

        return new EquipmentTransferBO(
                vo.getReferenceName(),
                vo.getSerialNumber(),
                vo.getSourceZoneName(),
                vo.getSourceSpaceName(),
                vo.getSourceLocalityName(),
                vo.getTargetZoneName(),
                vo.getTargetSpaceName(),
                vo.getTargetLocalityName(),
                vo.getTransferDate(),
                vo.getDetails()
        );
    }

    public EquipmentTransferVO boToVO(EquipmentTransferBO bo) {
        if (bo == null) return null;

        return new EquipmentTransferVO(
                bo.getReferenceName(),
                bo.getSerialNumber(),
                bo.getSourceZoneName(),
                bo.getSourceSpaceName(),
                bo.getSourceLocalityName(),
                bo.getTargetZoneName(),
                bo.getTargetSpaceName(),
                bo.getTargetLocalityName(),
                bo.getTransferDate(),
                bo.getDetails()
        );
    }

    public List<EquipmentTransferVO> boListToVOList(List<EquipmentTransferBO> bos) {
        if (bos == null) return null;
        return bos.stream()
                .map(this::boToVO)
                .collect(Collectors.toList());
    }

    public EquipmentTransferBO entityToBO(EquipmentTransfer entity) {
        if (entity == null) return null;

        return new EquipmentTransferBO(
                entity.getEquipment().getReferenceName(),
                entity.getEquipment().getSerialNumber(),
                entity.getFromZone(),
                entity.getFromSpace(),
                entity.getFromLocality(),
                entity.getToZone(),
                entity.getToSpace(),
                entity.getToLocality(),
                entity.getTransferDate(),
                entity.getDetails()
        );
    }
}
