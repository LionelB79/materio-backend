package com.materio.materio_backend.dto.Locality;
import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.jpa.entity.Locality;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocalityMapper {
@Autowired
    private SpaceMapper spaceMapper;

    public Locality boToEntity(LocalityBO bo) {
        if (bo == null) return null;

        Locality entity = new Locality();
        entity.setId(bo.getId());
        entity.setName(bo.getName());
        entity.setAddress(bo.getAddress());
        entity.setCp(bo.getCp());
        entity.setCity(bo.getCity());
        return entity;
    }



    public LocalityBO entityToBO(Locality entity) {
        if (entity == null) return null;

        LocalityBO bo = new LocalityBO();
        bo.setId(entity.getId());
        bo.setName(entity.getName());
        bo.setAddress(entity.getAddress());
        bo.setCp(entity.getCp());
        bo.setCity(entity.getCity());

        if (entity.getSpaces() != null) {
            Set<SpaceBO> spaces = entity.getSpaces().stream()
                    .map(space -> spaceMapper.entityToBO(space))
                    .collect(Collectors.toSet());
            bo.setSpaces(spaces);
        }

        return bo;
    }

    public LocalityVO boToVO(LocalityBO bo) {
        if (bo == null) return null;

        LocalityVO vo = new LocalityVO();
        vo.setId(bo.getId());
        vo.setName(bo.getName());
        vo.setAddress(bo.getAddress());
        vo.setCp(bo.getCp());
        vo.setCity(bo.getCity());

        if (bo.getSpaces() != null) {
            vo.setSpaces(bo.getSpaces().stream()
                    .map(spaceMapper::boToVO)
                    .collect(Collectors.toSet()));
        }

        return vo;
    }

    public LocalityBO voToBO(LocalityVO vo) {
        if (vo == null) return null;

        LocalityBO bo = new LocalityBO();
        bo.setId(vo.getId());
        bo.setName(vo.getName());
        bo.setAddress(vo.getAddress());
        bo.setCp(vo.getCp());
        bo.setCity(vo.getCity());

        if (vo.getSpaces() != null) {
            bo.setSpaces(vo.getSpaces().stream()
                    .map(spaceMapper::voToBO)
                    .collect(Collectors.toSet()));
        }

        return bo;
    }

    public List<LocalityVO> boListToVOList(List<LocalityBO> bos) {
        if (bos == null) return null;
        return bos.stream()
                .map(this::boToVO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromBO(Locality entity, LocalityBO bo) {
        if (entity == null || bo == null) return;

        entity.setName(bo.getName());
        entity.setAddress(bo.getAddress());
        entity.setCp(bo.getCp());
        entity.setCity(bo.getCity());

    }
}
