package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.dto.Space.SpaceBO;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.dto.Space.SpaceVO;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.business.service.SpaceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private SpaceMapper spaceMapper;

    @PostMapping(value ="/space",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpaceVO> createSpace(@Valid @RequestBody SpaceVO spaceVO) {
        SpaceBO spaceBO = spaceMapper.voToBO(spaceVO);
        SpaceBO createdSpace = spaceService.createSpace(spaceBO);
        return ResponseEntity.status(HttpStatus.CREATED).body(spaceMapper.boToVO(createdSpace));
    }

    @GetMapping("/space/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SpaceVO> getSpace(@PathVariable Long id) {
        SpaceBO space = spaceService.getSpace(id);
        return ResponseEntity.ok(spaceMapper.boToVO(space));
    }

    @PutMapping("/space/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpaceVO> updateSpace(
            @PathVariable Long id,
            @Valid @RequestBody SpaceVO spaceVO) {
        SpaceBO spaceBO = spaceMapper.voToBO(spaceVO);
        SpaceBO updatedSpace = spaceService.updateSpace(id, spaceBO);
        return ResponseEntity.ok(spaceMapper.boToVO(updatedSpace));
    }

    @DeleteMapping("/space/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        spaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/spaces/{localityId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<SpaceVO>> getSpacesByLocality(@PathVariable Long localityId) {
        Set<SpaceBO> spaces = spaceService.getSpacesByLocalityId(localityId);
        return ResponseEntity.ok(spaces.stream()
                .map(space -> spaceMapper.boToVO(space))
                .toList());
    }

    // Endpoint de compatibilité pour recherche par nom et localité
    @GetMapping("/space/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SpaceVO> getSpaceByNameAndLocality(
            @RequestParam Long localityId,
            @RequestParam String name) {
        SpaceBO space = spaceService.getSpaceByNameAndLocalityId(localityId, name);
        return ResponseEntity.ok(spaceMapper.boToVO(space));
    }
}
