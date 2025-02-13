package com.materio.materio_backend.view.controller;

import com.materio.materio_backend.business.service.LocalityService;
import com.materio.materio_backend.dto.Locality.LocalityBO;
import com.materio.materio_backend.jpa.entity.Locality;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}