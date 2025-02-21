package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.ZoneService;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.dto.Zone.ZoneVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/zones")
@Validated
public class ZoneController {

@Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneMapper zoneMapper;

    public ZoneController(ZoneService zoneService, ZoneMapper zoneMapper) {
        this.zoneService = zoneService;
        this.zoneMapper = zoneMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneVO createZone(@Valid @RequestBody ZoneVO zoneVO) {
        ZoneBO zoneBO = zoneMapper.voToBO(zoneVO);
        ZoneBO createdZone = zoneService.createZone(zoneBO);
        return zoneMapper.boToVO(createdZone);
    }

    @PutMapping("/{localityName}/{spaceName}/{zoneName}")
    public ZoneVO updateZone(
            @PathVariable String localityName,
            @PathVariable String spaceName,
            @PathVariable String zoneName,
            @Valid @RequestBody ZoneVO zoneVO) {

        // Vérification de cohérence des chemins
        if (!zoneName.equals(zoneVO.getName()) ||
                !spaceName.equals(zoneVO.getSpaceName()) ||
                !localityName.equals(zoneVO.getLocalityName())) {
            throw new IllegalArgumentException("Les informations de chemin ne correspondent pas aux données de la zone");
        }

        ZoneBO zoneBO = zoneMapper.voToBO(zoneVO);
        ZoneBO updatedZone = zoneService.updateZone(zoneBO);
        return zoneMapper.boToVO(updatedZone);
    }

    @DeleteMapping("/{localityName}/{spaceName}/{zoneName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZone(
            @PathVariable String localityName,
            @PathVariable String spaceName,
            @PathVariable String zoneName) {
        zoneService.deleteZone(localityName, spaceName, zoneName);
    }

    @GetMapping("/{localityName}/{spaceName}/{zoneName}")
    public ZoneVO getZone(
            @PathVariable String localityName,
            @PathVariable String spaceName,
            @PathVariable String zoneName) {
        ZoneBO zoneBO = zoneService.getZone(localityName, spaceName, zoneName);
        return zoneMapper.boToVO(zoneBO);
    }

    @GetMapping("/{localityName}/{spaceName}")
    public Set<ZoneVO> getZones(
            @PathVariable String localityName,
            @PathVariable String spaceName) {
        Set<ZoneBO> zones = zoneService.getZones(localityName, spaceName);
        return zones.stream()
                .map(zoneMapper::boToVO)
                .collect(Collectors.toSet());
    }
}
