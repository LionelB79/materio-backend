package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
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

    @PostMapping(value = "/locality", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Locality> createLocality(@Valid @RequestBody LocalityBO localityBO) {

        Locality createdLocality = localityService.createLocality(localityBO);
        return ResponseEntity.ok(createdLocality);

    }

    @PutMapping("/locality/{name}")
    public ResponseEntity<Locality> updateLocality(
            @PathVariable String name,
            @Valid @RequestBody LocalityBO localityBO) {

        Locality updatedLocality = localityService.updateLocality(name, localityBO);
        return ResponseEntity.ok(updatedLocality);

    }

    @DeleteMapping("/locality/{name}")
    public ResponseEntity<String> deleteLocality(@PathVariable String name) {

        localityService.deleteLocality(name);
        return ResponseEntity.ok("Le lieu :" +  name + "a été supprimé aveec succès");
    }

    @GetMapping("/locality/{name}")
    public ResponseEntity<Locality> getLocality(@PathVariable String name) {
        Locality locality = localityService.getLocalityByName(name);
        return ResponseEntity.ok(locality);
    }

    @GetMapping("/localities")
    public ResponseEntity<List<Locality>> getAllLocalities() {
        List<Locality> localities = localityService.getAllLocalities();
        return ResponseEntity.ok(localities);
    }
}