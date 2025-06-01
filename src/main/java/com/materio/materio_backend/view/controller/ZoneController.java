package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.ZoneService;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.dto.Zone.ZoneVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping(value = "/")
@Validated
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneMapper zoneMapper;

    @PostMapping(value ="/zone",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneVO createZone(@Valid @RequestBody ZoneVO zoneVO) {
        ZoneBO zoneBO = zoneMapper.voToBO(zoneVO);
        ZoneBO createdZone = zoneService.createZone(zoneBO);
        return zoneMapper.boToVO(createdZone);
    }

    @GetMapping("/zone/{id}")
    public ZoneVO getZone(@PathVariable Long id) {
        ZoneBO zoneBO = zoneService.getZone(id);
        return zoneMapper.boToVO(zoneBO);
    }

    @PutMapping("/zone/{id}")
    public ZoneVO updateZone(
            @PathVariable Long id,
            @Valid @RequestBody ZoneVO zoneVO) {
        ZoneBO zoneBO = zoneMapper.voToBO(zoneVO);
        ZoneBO updatedZone = zoneService.updateZone(id, zoneBO);
        return zoneMapper.boToVO(updatedZone);
    }

    @DeleteMapping("/zone/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
    }

    @GetMapping("/zones/{spaceId}")
    public Set<ZoneVO> getZonesBySpace(@PathVariable Long spaceId) {
        Set<ZoneBO> zones = zoneService.getZonesBySpaceId(spaceId);
        return zones.stream()
                .map(zoneMapper::boToVO)
                .collect(Collectors.toSet());
    }

    // Endpoint de compatibilité
    @GetMapping("/search")
    public ZoneVO getZoneByNameAndSpace(
            @RequestParam String name,
            @RequestParam Long spaceId) {
        ZoneBO zoneBO = zoneService.getZoneByNameAndSpaceId(name, spaceId);
        return zoneMapper.boToVO(zoneBO);
    }
}
