//package com.materio.materio_backend.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.materio.materio_backend.business.exception.equipment.DuplicateEquipmentException;
//import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
//import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
//import com.materio.materio_backend.business.exception.space.SpaceNotFoundException;
//import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
//import com.materio.materio_backend.business.service.EquipmentRefService;
//import com.materio.materio_backend.business.service.ZoneService;
//import com.materio.materio_backend.business.service.impl.EquipmentServiceImpl;
//import com.materio.materio_backend.dto.Equipment.EquipmentBO;
//import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
//import com.materio.materio_backend.dto.Zone.ZoneMapper;
//import com.materio.materio_backend.jpa.entity.Equipment;
//import com.materio.materio_backend.jpa.entity.EquipmentRef;
//import com.materio.materio_backend.jpa.entity.Locality;
//import com.materio.materio_backend.jpa.entity.Space;
//import com.materio.materio_backend.jpa.entity.Zone;
//import com.materio.materio_backend.jpa.repository.EquipmentRepository;
//import com.materio.materio_backend.jpa.repository.LocalityRepository;
//import com.materio.materio_backend.jpa.repository.SpaceRepository;
//import com.materio.materio_backend.jpa.repository.ZoneRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@ExtendWith(MockitoExtension.class)
//public class EquipmentServiceImplTest {
//
//    @Mock
//    private SpaceRepository spaceRepository;
//
//    @Mock
//    private EquipmentRepository equipmentRepo;
//
//    @Mock
//    private EquipmentRefService equipmentRefService;
//
//    @Mock
//    private LocalityRepository localityRepository;
//
//    @Mock
//    private ZoneService zoneService;
//
//    @Mock
//    private ZoneMapper zoneMapper;
//
//    @Mock
//    private EquipmentMapper equipmentMapper;
//
//    @Mock
//    private ZoneRepository zoneRepository;
//
//    @InjectMocks
//    private EquipmentServiceImpl equipmentService;
//
//    private EquipmentBO equipmentBO;
//    private Equipment equipment;
//    private Zone zone;
//    private Space space;
//    private Locality locality;
//    private EquipmentRef equipmentRef;
//
//    @BeforeEach
//    void initBeforeEach() {
//        locality = new Locality();
//        locality.setId(1L);
//        locality.setName("TestLocality");
//        locality.setAddress("123 Test Street");
//        locality.setCp(12345);
//        locality.setCity("Test City");
//
//        space = new Space();
//        space.setId(1L);
//        space.setName("TestSpace");
//        space.setLocality(locality);
//
//        zone = new Zone();
//        zone.setId(1L);
//        zone.setName("TestZone");
//        zone.setSpace(space);
//
//        equipmentRef = new EquipmentRef();
//        equipmentRef.setId(1L);
//        equipmentRef.setName("TestReference");
//        equipmentRef.setQuantity(1);
//
//        equipment = new Equipment();
//        equipment.setReferenceName("TestReference");
//        equipment.setSerialNumber("TestSerial");
//        equipment.setPurchaseDate(LocalDate.now());
//        equipment.setMark("TestMark");
//        equipment.setDescription("Test description");
//        equipment.setZone(zone);
//        equipment.setTag("TestTag");
//        equipment.setBarcode(123);
//
//        equipmentBO = new EquipmentBO();
//        equipmentBO.setReferenceName("TestReference");
//        equipmentBO.setSerialNumber("TestSerial");
//        equipmentBO.setPurchaseDate(LocalDate.now());
//        equipmentBO.setMark("TestMark");
//        equipmentBO.setDescription("Test description");
//        equipmentBO.setZoneName("TestZone");
//        equipmentBO.setSpaceName("TestSpace");
//        equipmentBO.setLocalityName("TestLocality");
//        equipmentBO.setTag("TestTag");
//        equipmentBO.setBarCode(123);
//    }
//
//    @Test
//    void createEquipment_Success() {
//        when(equipmentRepo.existsBySerialNumberAndReferenceName("TestSerial", "TestReference"))
//                .thenReturn(false);
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality"))
//                .thenReturn(Optional.of(zone));
//        when(equipmentMapper.boToEntity(equipmentBO)).thenReturn(equipment);
//        when(equipmentRefService.getOrCreateReference("TestReference")).thenReturn(equipmentRef);
//        when(equipmentRepo.save(equipment)).thenReturn(equipment);
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        EquipmentBO result = equipmentService.createEquipment(equipmentBO);
//
//        assertNotNull(result);
//        assertEquals("TestReference", result.getReferenceName());
//        assertEquals("TestSerial", result.getSerialNumber());
//
//        verify(equipmentRepo).existsBySerialNumberAndReferenceName("TestSerial", "TestReference");
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
//        verify(equipmentMapper).boToEntity(equipmentBO);
//        verify(equipmentRefService).getOrCreateReference("TestReference");
//        verify(equipmentRepo).save(equipment);
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//
//    @Test
//    void createEquipment_DuplicateEquipment_ThrowsException() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference"))
//                .thenReturn(Optional.of(equipment));
//
//        assertThrows(DuplicateEquipmentException.class, () -> {
//            equipmentService.createEquipment(equipmentBO);
//        });
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference");
//        verify(equipmentRepo, never()).save(any(Equipment.class));
//    }
//
//    @Test
//    void createEquipment_ZoneNotFound_ThrowsException() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference"))
//                .thenReturn(Optional.empty());
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ZoneNotFoundException.class, () -> {
//            equipmentService.createEquipment(equipmentBO);
//        });
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference");
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
//        verify(equipmentRepo, never()).save(any(Equipment.class));
//    }
//
//    @Test
//    void getEquipment_Success() {
//        Long equipmentId = 1L;
//        equipment.setId(equipmentId);
//
//        when(equipmentRepo.findById(equipmentId))
//                .thenReturn(Optional.of(equipment));
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        EquipmentBO result = equipmentService.getEquipment(equipmentId);
//
//        assertNotNull(result);
//        assertEquals("TestReference", result.getReferenceName());
//        assertEquals("TestSerial", result.getSerialNumber());
//
//        verify(equipmentRepo).findById(equipmentId);
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//
//    @Test
//    void getEquipment_NotFound_ThrowsException() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("NonExistentSerial", "NonExistentRef"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(EquipmentNotFoundException.class, () -> {
//            equipmentService.getEquipment("NonExistentSerial", "NonExistentRef");
//        });
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("NonExistentSerial", "NonExistentRef");
//    }
//
//    @Test
//    void updateEquipment_Success() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference"))
//                .thenReturn(Optional.of(equipment));
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality"))
//                .thenReturn(Optional.of(zone));
//        when(equipmentRepo.save(equipment)).thenReturn(equipment);
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        EquipmentBO result = equipmentService.updateEquipment(equipmentBO);
//
//        assertNotNull(result);
//        assertEquals("TestReference", result.getReferenceName());
//        assertEquals("TestSerial", result.getSerialNumber());
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference");
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
//        verify(equipmentRepo).save(equipment);
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//
//    @Test
//    void updateEquipment_EquipmentNotFound_ThrowsException() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(EquipmentNotFoundException.class, () -> {
//            equipmentService.updateEquipment(equipmentBO);
//        });
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference");
//        verify(equipmentRepo, never()).save(any(Equipment.class));
//    }
//
//    @Test
//    void updateEquipment_ZoneNotFound_ThrowsException() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference"))
//                .thenReturn(Optional.of(equipment));
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ZoneNotFoundException.class, () -> {
//            equipmentService.updateEquipment(equipmentBO);
//        });
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("TestSerial", "TestReference");
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
//        verify(equipmentRepo, never()).save(any(Equipment.class));
//    }
//
//    void deleteEquipment_Success() {
//        Long equipmentId = 1L;
//        equipment.setId(equipmentId);
//
//        when(equipmentRepo.findById(equipmentId))
//                .thenReturn(Optional.of(equipment));
//        doNothing().when(equipmentRefService).decrementQuantity("TestReference");
//        doNothing().when(equipmentRepo).delete(equipment);
//
//        equipmentService.deleteEquipment(equipmentId);
//
//        verify(equipmentRepo).findById(equipmentId);
//        verify(equipmentRefService).decrementQuantity("TestReference");
//        verify(equipmentRepo).delete(equipment);
//    }
//
//    @Test
//    void deleteEquipment_NotFound_ThrowsException() {
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("NonExistentSerial", "NonExistentRef"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(EquipmentNotFoundException.class, () -> {
//            equipmentService.deleteEquipment("NonExistentSerial", "NonExistentRef");
//        });
//
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("NonExistentSerial", "NonExistentRef");
//        verify(equipmentRefService, never()).decrementQuantity(anyString());
//        verify(equipmentRepo, never()).delete(any(Equipment.class));
//    }
//
//    @Test
//    void getEquipmentsByZone_Success() {
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality"))
//                .thenReturn(Optional.of(zone));
//        when(equipmentRepo.findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName("TestZone", "TestSpace", "TestLocality"))
//                .thenReturn(Set.of(equipment));
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        Set<EquipmentBO> results = equipmentService.getEquipmentsByZone("TestLocality", "TestSpace", "TestZone");
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals("TestReference", results.iterator().next().getReferenceName());
//
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
//        verify(equipmentRepo).findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName("TestZone", "TestSpace", "TestLocality");
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//
//    @Test
//    void getEquipmentsByZone_ZoneNotFound_ThrowsException() {
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName("NonExistentZone", "TestSpace", "TestLocality"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ZoneNotFoundException.class, () -> {
//            equipmentService.getEquipmentsByZone("TestLocality", "TestSpace", "NonExistentZone");
//        });
//
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName("NonExistentZone", "TestSpace", "TestLocality");
//        verify(equipmentRepo, never()).findByZoneNameAndZoneSpaceNameAndZoneSpaceLocalityName(anyString(), anyString(), anyString());
//    }
//
//    @Test
//    void getEquipmentsBySpace_Success() {
//        when(spaceRepository.findByNameAndLocality_Name("TestSpace", "TestLocality"))
//                .thenReturn(Optional.of(space));
//        when(equipmentRepo.findByZoneSpaceNameAndZoneSpaceLocalityName("TestSpace", "TestLocality"))
//                .thenReturn(Set.of(equipment));
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        Set<EquipmentBO> results = equipmentService.getEquipmentsBySpace("TestLocality", "TestSpace");
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals("TestReference", results.iterator().next().getReferenceName());
//
//        verify(spaceRepository).findByNameAndLocality_Name("TestSpace", "TestLocality");
//        verify(equipmentRepo).findByZoneSpaceNameAndZoneSpaceLocalityName("TestSpace", "TestLocality");
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//
//    @Test
//    void getEquipmentsBySpace_SpaceNotFound_ThrowsException() {
//        when(spaceRepository.findByNameAndLocality_Name("NonExistentSpace", "TestLocality"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(SpaceNotFoundException.class, () -> {
//            equipmentService.getEquipmentsBySpace("TestLocality", "NonExistentSpace");
//        });
//
//        verify(spaceRepository).findByNameAndLocality_Name("NonExistentSpace", "TestLocality");
//        verify(equipmentRepo, never()).findByZoneSpaceNameAndZoneSpaceLocalityName(anyString(), anyString());
//    }
//
//    @Test
//    void getEquipmentsByLocality_Success() {
//        when(localityRepository.findByName("TestLocality")).thenReturn(Optional.of(locality));
//        when(equipmentRepo.findByZoneSpaceLocalityName("TestLocality")).thenReturn(Set.of(equipment));
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        Set<EquipmentBO> results = equipmentService.getEquipmentsByLocality("TestLocality");
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals("TestReference", results.iterator().next().getReferenceName());
//
//        verify(localityRepository).findByName("TestLocality");
//        verify(equipmentRepo).findByZoneSpaceLocalityName("TestLocality");
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//
//    @Test
//    void getEquipmentsByLocality_LocalityNotFound_ThrowsException() {
//        when(localityRepository.findByName("NonExistentLocality")).thenReturn(Optional.empty());
//
//        assertThrows(LocalityNotFoundException.class, () -> {
//            equipmentService.getEquipmentsByLocality("NonExistentLocality");
//        });
//
//        verify(localityRepository).findByName("NonExistentLocality");
//        verify(equipmentRepo, never()).findByZoneSpaceLocalityName(anyString());
//    }
//
//    @Test
//    void getAllEquipments_Success() {
//        when(equipmentRepo.findAll()).thenReturn(List.of(equipment));
//        when(equipmentMapper.entityToBO(equipment)).thenReturn(equipmentBO);
//
//        Set<EquipmentBO> results = equipmentService.getAllEquipments();
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals("TestReference", results.iterator().next().getReferenceName());
//
//        verify(equipmentRepo).findAll();
//        verify(equipmentMapper).entityToBO(equipment);
//    }
//}