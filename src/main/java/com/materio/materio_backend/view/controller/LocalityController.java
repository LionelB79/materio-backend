package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.Locality.LocalityMapper;
import com.materio.materio_backend.dto.Locality.LocalityVO;
import com.materio.materio_backend.jpa.entity.Locality;
import jakarta.validation.Valid;
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
public class LocalityController {

    @Autowired
    private LocalityService localityService;

    @Autowired
    private LocalityMapper localityMapper;

    @PostMapping(value ="/locality",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocalityVO> createLocality(@Valid @RequestBody final LocalityBO localityBO) {
        final LocalityBO createdLocality = localityService.createLocality(localityBO);
        LocalityVO localityVO = localityMapper.boToVO(createdLocality);
        return ResponseEntity.status(HttpStatus.CREATED).body(localityVO);
    }

    @GetMapping("/locality/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<LocalityVO> getLocality(@PathVariable Long id) {
        final LocalityBO localityBO = localityService.getLocality(id);
        return ResponseEntity.ok(localityMapper.boToVO(localityBO));
    }

    @PutMapping("/locality/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocalityVO> updateLocality(
            @PathVariable Long id,
            @Valid @RequestBody LocalityBO localityBO) {
        final LocalityBO updatedLocality = localityService.updateLocality(id, localityBO);
        return ResponseEntity.ok(localityMapper.boToVO(updatedLocality));
    }

    @DeleteMapping("/locality/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLocality(@PathVariable Long id) {
        localityService.deleteLocality(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/localities")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<LocalityVO>> getAllLocalities() {
        final Set<LocalityBO> localities = localityService.getAllLocalities();
        return ResponseEntity.ok(localities.stream()
                .map(localityMapper::boToVO)
                .toList());
    }

    // Endpoint de recherche par nom (pour la compatibilité)
    @GetMapping("/locality/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<LocalityVO> getLocalityByName(@RequestParam String name) {
        final LocalityBO localityBO = localityService.getLocalityByName(name);
        return ResponseEntity.ok(localityMapper.boToVO(localityBO));
    }
}