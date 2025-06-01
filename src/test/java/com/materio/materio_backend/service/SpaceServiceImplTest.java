//package com.materio.materio_backend.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
//import com.materio.materio_backend.business.exception.space.DuplicateSpaceException;
//import com.materio.materio_backend.business.exception.space.SpaceHasEquipedZonesException;
//import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
//import com.materio.materio_backend.business.service.impl.SpaceServiceImpl;
//import com.materio.materio_backend.dto.Locality.LocalityMapper;
//import com.materio.materio_backend.dto.Space.SpaceBO;
//import com.materio.materio_backend.dto.Space.SpaceMapper;
//import com.materio.materio_backend.dto.Zone.ZoneBO;
//import com.materio.materio_backend.jpa.entity.Locality;
//import com.materio.materio_backend.jpa.entity.Space;
//import com.materio.materio_backend.jpa.repository.LocalityRepository;
//import com.materio.materio_backend.jpa.repository.SpaceRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//@ExtendWith(MockitoExtension.class)
//public class SpaceServiceImplTest {
//
//    @Mock
//    private SpaceRepository spaceRepo;
//
//    @Mock
//    private SpaceMapper spaceMapper;
//
//    @Mock
//    private LocalityMapper localityMapper;
//
//    @Mock
//    private LocalityRepository localityRepository;
//
//    @InjectMocks
//    private SpaceServiceImpl spaceService;
//
//    private Locality locality;
//    private Space space;
//    private SpaceBO spaceBO;
//    private ZoneBO zoneBO;
//
//    @BeforeEach
//    void initBeforeEach() {
//        // Initialisation de la localité
//        locality = new Locality();
//        locality.setName("TestLocality");
//        locality.setAddress("123 Test Street");
//        locality.setCp(12345);
//        locality.setCity("Test City");
//        locality.setSpaces(new HashSet<>());
//
//        // Initialisation de l'espace
//        space = new Space();
//        space.setId(1L);
//        space.setName("TestSpace");
//        space.setLocality(locality);
//        space.setZones(new HashSet<>());
//
//        // Initialisation du SpaceBO
//        spaceBO = new SpaceBO();
//        spaceBO.setId(1L);
//        spaceBO.setName("TestSpace");
//        spaceBO.setLocalityName("TestLocality");
//        spaceBO.setZones(new HashSet<>());
//
//        // Initialisation d'une zone pour les tests
//        zoneBO = new ZoneBO();
//        zoneBO.setName("TestZone");
//        zoneBO.setSpaceName("TestSpace");
//        zoneBO.setLocalityName("TestLocality");
//        zoneBO.setEquipments(new HashSet<>());
//    }
//
//    @Test
//    void createSpace_Success() {
//        when(localityRepository.findByName("TestLocality")).thenReturn(Optional.of(locality));
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.empty());
//        when(spaceMapper.boToEntity(spaceBO)).thenReturn(space);
//        when(spaceRepo.save(space)).thenReturn(space);
//        when(spaceMapper.entityToBO(space)).thenReturn(spaceBO);
//
//        SpaceBO result = spaceService.createSpace(spaceBO);
//
//        assertNotNull(result);
//        assertEquals("TestSpace", result.getName());
//        assertEquals("TestLocality", result.getLocalityName());
//
//        verify(localityRepository).findByName("TestLocality");
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceMapper).boToEntity(spaceBO);
//        verify(spaceRepo).save(space);
//        verify(spaceMapper).entityToBO(space);
//    }
//
//    @Test
//    void createSpace_LocalityNotFound_ThrowsException() {
//        when(localityRepository.findByName("TestLocality")).thenReturn(Optional.empty());
//
//        assertThrows(LocalityNotFoundException.class, () -> {
//            spaceService.createSpace(spaceBO);
//        });
//
//        verify(localityRepository).findByName("TestLocality");
//        verify(spaceRepo, never()).save(any(Space.class));
//    }
//
//    @Test
//    void createSpace_DuplicateSpace_ThrowsException() {
//        when(localityRepository.findByName("TestLocality")).thenReturn(Optional.of(locality));
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
//
//        assertThrows(DuplicateSpaceException.class, () -> {
//            spaceService.createSpace(spaceBO);
//        });
//
//        verify(localityRepository).findByName("TestLocality");
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceRepo, never()).save(any(Space.class));
//    }
//
//    @Test
//    void updateSpace_Success() {
//        SpaceBO updatedSpaceBO = new SpaceBO();
//        updatedSpaceBO.setId(1L);
//        updatedSpaceBO.setName("UpdatedSpace");
//        updatedSpaceBO.setLocalityName("TestLocality");
//
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
//        when(spaceRepo.findByName("UpdatedSpace")).thenReturn(Optional.empty());
//        when(spaceRepo.save(space)).thenReturn(space);
//        when(spaceMapper.entityToBO(space)).thenReturn(updatedSpaceBO);
//
//        SpaceBO result = spaceService.updateSpace("TestLocality", "TestSpace", updatedSpaceBO);
//
//        assertNotNull(result);
//        assertEquals("UpdatedSpace", result.getName());
//
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceRepo).findByName("UpdatedSpace");
//        verify(spaceRepo).save(space);
//    }
//
//    @Test
//    void updateSpace_SpaceNotFound_ThrowsException() {
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.empty());
//
//        assertThrows(SpaceNotFoundException.class, () -> {
//            spaceService.updateSpace("TestLocality", "TestSpace", spaceBO);
//        });
//
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceRepo, never()).save(any(Space.class));
//    }
//
//    @Test
//    void updateSpace_DuplicateName_ThrowsException() {
//        SpaceBO updatedSpaceBO = new SpaceBO();
//        updatedSpaceBO.setId(1L);
//        updatedSpaceBO.setName("NewSpace");
//        updatedSpaceBO.setLocalityName("TestLocality");
//
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
//        when(spaceRepo.findByName("NewSpace")).thenReturn(Optional.of(new Space()));
//
//        assertThrows(DuplicateSpaceException.class, () -> {
//            spaceService.updateSpace("TestLocality", "TestSpace", updatedSpaceBO);
//        });
//
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceRepo).findByName("NewSpace");
//        verify(spaceRepo, never()).save(any(Space.class));
//    }
//
//    @Test
//    void getSpace_Success() {
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
//        when(spaceMapper.entityToBO(space)).thenReturn(spaceBO);
//
//        SpaceBO result = spaceService.getSpace("TestLocality", "TestSpace");
//
//        assertNotNull(result);
//        assertEquals("TestSpace", result.getName());
//        assertEquals("TestLocality", result.getLocalityName());
//
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceMapper).entityToBO(space);
//    }
//
//    @Test
//    void getSpace_NotFound_ThrowsException() {
//        when(spaceRepo.findByNameAndLocality_Name("NonExistentSpace", "TestLocality")).thenReturn(Optional.empty());
//
//        assertThrows(SpaceNotFoundException.class, () -> {
//            spaceService.getSpace("TestLocality", "NonExistentSpace");
//        });
//
//        verify(spaceRepo).findByNameAndLocality_Name("NonExistentSpace", "TestLocality");
//    }
//
//    @Test
//    void getSpacesByLocality_Success() {
//        Set<Space> spaces = Set.of(space);
//        when(localityRepository.findByName("TestLocality")).thenReturn(Optional.of(locality));
//        when(spaceRepo.findByLocality_Name("TestLocality")).thenReturn(spaces);
//        when(spaceMapper.entityToBO(space)).thenReturn(spaceBO);
//
//        Set<SpaceBO> results = spaceService.getSpacesByLocality("TestLocality");
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals("TestSpace", results.iterator().next().getName());
//
//        verify(localityRepository).findByName("TestLocality");
//        verify(spaceRepo).findByLocality_Name("TestLocality");
//        verify(spaceMapper).entityToBO(space);
//    }
//
//    @Test
//    void getSpacesByLocality_LocalityNotFound_ThrowsException() {
//        when(localityRepository.findByName("NonExistentLocality")).thenReturn(Optional.empty());
//
//        assertThrows(LocalityNotFoundException.class, () -> {
//            spaceService.getSpacesByLocality("NonExistentLocality");
//        });
//
//        verify(localityRepository).findByName("NonExistentLocality");
//        verify(spaceRepo, never()).findByLocality_Name(anyString());
//    }
//
//    @Test
//    void deleteSpace_Success() {
//        Set<ZoneBO> zonesWithoutEquipment = new HashSet<>();
//        zonesWithoutEquipment.add(zoneBO); // Zone sans équipements
//        spaceBO.setZones(zonesWithoutEquipment);
//
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
//        when(spaceMapper.entityToBO(space)).thenReturn(spaceBO);
//        when(spaceMapper.boToEntity(spaceBO)).thenReturn(space);
//
//        spaceService.deleteSpace("TestLocality", "TestSpace");
//
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceMapper).entityToBO(space);
//        verify(spaceRepo).delete(space);
//    }
//
//    @Test
//    void deleteSpace_WithEquipment_ThrowsException() {
//        ZoneBO zoneWithEquipment = new ZoneBO();
//        zoneWithEquipment.setName("ZoneWithEquipment");
//        zoneWithEquipment.setSpaceName("TestSpace");
//        zoneWithEquipment.setLocalityName("TestLocality");
//        zoneWithEquipment.setEquipments(Set.of(new com.materio.materio_backend.dto.Equipment.EquipmentBO())); // Zone avec des équipements
//
//        spaceBO.setZones(Set.of(zoneWithEquipment));
//
//        when(spaceRepo.findByNameAndLocality_Name("TestSpace", "TestLocality")).thenReturn(Optional.of(space));
//        when(spaceMapper.entityToBO(space)).thenReturn(spaceBO);
//
//        assertThrows(SpaceHasEquipedZonesException.class, () -> {
//            spaceService.deleteSpace("TestLocality", "TestSpace");
//        });
//
//        verify(spaceRepo).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(spaceMapper).entityToBO(space);
//        verify(spaceRepo, never()).delete(any(Space.class));
//    }
//}