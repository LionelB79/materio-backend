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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private SpaceMapper spaceMapper;

    @PostMapping(value = "/space")
    public ResponseEntity<String> createSpace(@Valid @RequestBody SpaceVO spaceVO) {

        spaceService.createSpace(spaceMapper.voToBO(spaceVO));
        return ResponseEntity.ok("l'espace" + spaceVO.getName() + " a été créé");
    }


    @DeleteMapping(value = "/space/{spaceName}")
    public ResponseEntity<String> deleteRoom(@RequestParam String locality, @PathVariable String spaceName) {

        spaceService.deleteSpace(locality, spaceName);
        return ResponseEntity.ok("La salle " + spaceName + " a été supprimée");
    }

    @GetMapping(value = "/spaces")
    public ResponseEntity<List<SpaceVO>> getAllSapces(@RequestParam String localityName) {
        Set<SpaceBO> spaces = spaceService.getSpacesByLocality(localityName);

        return ResponseEntity.ok(spaces.stream()
                .map(space -> spaceMapper.boToVO(space))
                .toList());
    }

    @GetMapping(value = "/space")
    public ResponseEntity<SpaceVO> getSpace(@RequestParam String localityName, @PathVariable String spaceName) {
        SpaceBO space = spaceService.getSpace(localityName, spaceName);

        return ResponseEntity.ok(spaceMapper.boToVO(space));
    }
}
