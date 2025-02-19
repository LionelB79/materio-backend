package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.dto.Locality.LocalityMapper;
import com.materio.materio_backend.dto.Locality.LocalityVO;
import com.materio.materio_backend.jpa.entity.Locality;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocalityController {

    @Autowired
    private LocalityService localityService;

    @Autowired
    private LocalityMapper localityMapper;

    @PostMapping(value = "/locality", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLocality(@Valid @RequestBody final LocalityBO localityBO) {

        final Locality createdLocality = localityService.createLocality(localityBO);
        return ResponseEntity.ok("OK");

    }

    @PutMapping("/locality/{name}")
    public ResponseEntity<LocalityVO> updateLocality(
            @PathVariable String name,
            @Valid @RequestBody LocalityBO localityBO) {

        final Locality updatedLocality = localityService.updateLocality(name, localityBO);
        return ResponseEntity.ok(localityMapper.EntityToVO(updatedLocality));
    }

    @DeleteMapping("/locality/{name}")
    public ResponseEntity<String> deleteLocality(@PathVariable String name) {

        localityService.deleteLocality(name);
        return ResponseEntity.ok("Le lieu :" + name + " a été supprimé aveec succès");
    }

    @GetMapping("/locality/{name}")
    public ResponseEntity<LocalityVO> getLocality(@PathVariable String name) {
        final Locality locality = localityService.getLocalityByName(name);

        return ResponseEntity.ok(localityMapper.EntityToVO(locality));
    }

    @GetMapping("/localities")
    public ResponseEntity<List<LocalityVO>> getAllLocalities() {
        final List<Locality> localities = localityService.getAllLocalities();

        return ResponseEntity.ok(localities.stream()
                .map((locality) -> localityMapper.EntityToVO(locality)
                ).toList());
    }
}