package com.materio.materio_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
import com.materio.materio_backend.business.exception.zone.DuplicateZoneException;
import com.materio.materio_backend.business.exception.zone.ZoneNotEmptyException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import com.materio.materio_backend.business.service.impl.ZoneServiceImpl;
import com.materio.materio_backend.dto.Space.SpaceMapper;
import com.materio.materio_backend.dto.Zone.ZoneBO;
import com.materio.materio_backend.dto.Zone.ZoneMapper;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Space;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import com.materio.materio_backend.jpa.repository.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ZoneServiceImplTest {
    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private ZoneMapper zoneMapper;

    @Mock
    private SpaceMapper spaceMapper;

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private ZoneServiceImpl zoneService;

    private Locality locality;
    private Space space;
    private Zone zone;
    private ZoneBO zoneBO;

    @BeforeEach
    void initBeforeEach() {
        // Initialisation de la localité
        locality = new Locality();
        locality.setName("TestLocality");
        locality.setAddress("123 Test Street");
        locality.setCp(12345);
        locality.setCity("Test City");

        // Initialisation de l'espace
        space = new Space();
        space.setId(1L);
        space.setName("TestSpace");
        space.setLocality(locality);

        // Initialisation de la zone
        zone = new Zone();
        zone.setId(1L);
        zone.setName("TestZone");
        zone.setDescription("Test zone description");
        zone.setSpace(space);
        zone.setEquipments(new HashSet<>());

        zoneBO = new ZoneBO();
        zoneBO.setName("TestZone");
        zoneBO.setDescription("Test zone description");
        zoneBO.setSpaceName("TestSpace");
        zoneBO.setLocalityName("TestLocality");
        zoneBO.setEquipments(new HashSet<>());
    }

    @Test
    void createZone_Success() {
        when(spaceRepository.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.empty());
        when(zoneMapper.boToEntity(zoneBO)).thenReturn(zone);
        when(zoneRepository.save(zone)).thenReturn(zone);
        when(zoneMapper.entityToBO(zone)).thenReturn(zoneBO);

        ZoneBO result = zoneService.createZone(zoneBO);

        assertNotNull(result);
        assertEquals("TestZone", result.getName());
        assertEquals("TestSpace", result.getSpaceName());
        assertEquals("TestLocality", result.getLocalityName());

        verify(spaceRepository).findByNameAndLocality_Name("TestSpace", "TestLocality");
        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneMapper).boToEntity(zoneBO);
        verify(zoneRepository).save(zone);
        verify(zoneMapper).entityToBO(zone);
    }

    @Test
    void createZone_SpaceNotFound_ThrowsException() {
        when(spaceRepository.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.empty());

        assertThrows(SpaceNotFoundException.class, () -> {
            zoneService.createZone(zoneBO);
        });

        verify(spaceRepository).findByNameAndLocality_Name("TestSpace", "TestLocality");
        verify(zoneRepository, never()).save(any(Zone.class));
    }

    @Test
    void createZone_DuplicateZone_ThrowsException() {
        when(spaceRepository.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.of(zone));

        assertThrows(DuplicateZoneException.class, () -> {
            zoneService.createZone(zoneBO);
        });

        verify(spaceRepository).findByNameAndLocality_Name("TestSpace", "TestLocality");
        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneRepository, never()).save(any(Zone.class));
    }

    @Test
    void updateZone_Success() {
        ZoneBO updatedZoneBO = new ZoneBO();
        updatedZoneBO.setName("TestZone");
        updatedZoneBO.setDescription("Updated description");
        updatedZoneBO.setSpaceName("TestSpace");
        updatedZoneBO.setLocalityName("TestLocality");

        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.of(zone));
        when(zoneRepository.save(zone)).thenReturn(zone);
        when(zoneMapper.entityToBO(zone)).thenReturn(updatedZoneBO);

        ZoneBO result = zoneService.updateZone(updatedZoneBO);

        assertNotNull(result);
        assertEquals("TestZone", result.getName());
        assertEquals("Updated description", result.getDescription());

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneMapper).updateEntityFromBO(zone, updatedZoneBO);
        verify(zoneRepository).save(zone);
        verify(zoneMapper).entityToBO(zone);
    }

    @Test
    void updateZone_ZoneNotFound_ThrowsException() {
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> {
            zoneService.updateZone(zoneBO);
        });

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneRepository, never()).save(any(Zone.class));
    }

    @Test
    void deleteZone_Success() {
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.of(zone));

        zoneService.deleteZone("TestLocality", "TestSpace", "TestZone");

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneRepository).delete(zone);
    }

    @Test
    void deleteZone_ZoneNotFound_ThrowsException() {
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("NonExistentZone", "TestSpace", "TestLocality")).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> {
            zoneService.deleteZone("TestLocality", "TestSpace", "NonExistentZone");
        });

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("NonExistentZone", "TestSpace", "TestLocality");
        verify(zoneRepository, never()).delete(any(Zone.class));
    }

    @Test
    void deleteZone_WithEquipment_ThrowsException() {
        Zone zoneWithEquipment = new Zone();
        zoneWithEquipment.setId(1L);
        zoneWithEquipment.setName("TestZone");
        zoneWithEquipment.setSpace(space);

        Set<Equipment> equipments = new HashSet<>();
        Equipment equipment = new Equipment();
        equipments.add(equipment);
        zoneWithEquipment.setEquipments(equipments);

        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.of(zoneWithEquipment));

        assertThrows(ZoneNotEmptyException.class, () -> {
            zoneService.deleteZone("TestLocality", "TestSpace", "TestZone");
        });

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneRepository, never()).delete(any(Zone.class));
    }

    @Test
    void getZone_Success() {
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality")).thenReturn(Optional.of(zone));
        when(zoneMapper.entityToBO(zone)).thenReturn(zoneBO);

        ZoneBO result = zoneService.getZone("TestLocality", "TestSpace", "TestZone");

        assertNotNull(result);
        assertEquals("TestZone", result.getName());
        assertEquals("TestSpace", result.getSpaceName());
        assertEquals("TestLocality", result.getLocalityName());

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
        verify(zoneMapper).entityToBO(zone);
    }

    @Test
    void getZone_NotFound_ThrowsException() {
        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("NonExistentZone", "TestSpace", "TestLocality")).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> {
            zoneService.getZone("TestLocality", "TestSpace", "NonExistentZone");
        });

        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("NonExistentZone", "TestSpace", "TestLocality");
    }

    @Test
    void getZones_Success() {
        List<Zone> zones = List.of(zone);

        when(zoneRepository.findBySpaceNameAndSpaceLocalityName("TestSpace", "TestLocality")).thenReturn(zones);
        when(zoneMapper.entityToBO(zone)).thenReturn(zoneBO);

        Set<ZoneBO> results = zoneService.getZones("TestLocality", "TestSpace");

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("TestZone", results.iterator().next().getName());

        verify(zoneRepository).findBySpaceNameAndSpaceLocalityName("TestSpace", "TestLocality");
        verify(zoneMapper).entityToBO(zone);
    }

    @Test
    void getZones_EmptyList() {
        when(zoneRepository.findBySpaceNameAndSpaceLocalityName("TestSpace", "TestLocality")).thenReturn(Collections.emptyList());

        Set<ZoneBO> results = zoneService.getZones("TestLocality", "TestSpace");

        assertNotNull(results);
        assertTrue(results.isEmpty());

        verify(zoneRepository).findBySpaceNameAndSpaceLocalityName("TestSpace", "TestLocality");
    }
}