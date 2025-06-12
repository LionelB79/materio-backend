//package com.materio.materio_backend.service;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.materio.materio_backend.business.exception.equipment.EquipmentLocationMismatchException;
//import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
//import com.materio.materio_backend.business.exception.transfer.TransferValidationException;
//import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
//import com.materio.materio_backend.business.service.impl.EquipmentTransferServiceImpl;
//import com.materio.materio_backend.dto.Equipment.EquipmentBO;
//import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
//import com.materio.materio_backend.dto.Transfer.EquipmentToTransfer;
//import com.materio.materio_backend.dto.Transfer.EquipmentTransferBO;
//import com.materio.materio_backend.dto.Transfer.EquipmentTransferMapper;
//import com.materio.materio_backend.jpa.entity.Equipment;
//import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
//import com.materio.materio_backend.jpa.entity.Locality;
//import com.materio.materio_backend.jpa.entity.Space;
//import com.materio.materio_backend.jpa.entity.Zone;
//import com.materio.materio_backend.jpa.repository.EquipmentRepository;
//import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
//import com.materio.materio_backend.jpa.repository.ZoneRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@ExtendWith(MockitoExtension.class)
//public class EquipmentTransferServiceImplTest {
//
//    @Mock
//    private EquipmentRepository equipmentRepo;
//
//    @Mock
//    private EquipmentTransferRepository equipmentTransferRepo;
//
//    @Mock
//    private ZoneRepository zoneRepository;
//
//    @Mock
//    private EquipmentMapper equipmentMapper;
//
//    @Mock
//    private EquipmentTransferMapper transferMapper;
//
//    @InjectMocks
//    private EquipmentTransferServiceImpl equipmentTransferService;
//
//    private Equipment equipment1;
//    private Equipment equipment2;
//    private EquipmentBO equipmentBO1;
//    private EquipmentBO equipmentBO2;
//    private EquipmentToTransfer equipmentToTransfer1;
//    private EquipmentToTransfer equipmentToTransfer2;
//    private EquipmentTransferBO transferBO;
//    private Zone sourceZone;
//    private Zone targetZone;
//    private Space sourceSpace;
//    private Space targetSpace;
//    private Locality sourceLocality;
//    private Locality targetLocality;
//    private EquipmentTransfer savedTransfer;
//    private EquipmentTransferBO savedTransferBO;
//
//    @BeforeEach
//    void initBeforeEach() {
//        sourceLocality = new Locality();
//        sourceLocality.setName("SourceLocality");
//
//        targetLocality = new Locality();
//        targetLocality.setName("TargetLocality");
//
//        // Setup espaces
//        sourceSpace = new Space();
//        sourceSpace.setName("SourceSpace");
//        sourceSpace.setLocality(sourceLocality);
//
//        targetSpace = new Space();
//        targetSpace.setName("TargetSpace");
//        targetSpace.setLocality(targetLocality);
//
//        // Setup zones
//        sourceZone = new Zone();
//        sourceZone.setName("SourceZone");
//        sourceZone.setSpace(sourceSpace);
//
//        targetZone = new Zone();
//        targetZone.setName("TargetZone");
//        targetZone.setSpace(targetSpace);
//
//        // Setup équipements
//        equipment1 = new Equipment();
//        EquipmentPK pk1 = new EquipmentPK();
//        pk1.setReferenceName("Ref1");
//        pk1.setSerialNumber("SN1");
//        equipment1.setId(pk1);
//        equipment1.setZone(sourceZone);
//
//        equipment2 = new Equipment();
//        EquipmentPK pk2 = new EquipmentPK();
//        pk2.setReferenceName("Ref2");
//        pk2.setSerialNumber("SN2");
//        equipment2.setId(pk2);
//        equipment2.setZone(sourceZone);
//
//        // Setup EquipmentBOs
//        equipmentBO1 = new EquipmentBO();
//        equipmentBO1.setReferenceName("Ref1");
//        equipmentBO1.setSerialNumber("SN1");
//        equipmentBO1.setZoneName("SourceZone");
//        equipmentBO1.setSpaceName("SourceSpace");
//        equipmentBO1.setLocalityName("SourceLocality");
//
//        equipmentBO2 = new EquipmentBO();
//        equipmentBO2.setReferenceName("Ref2");
//        equipmentBO2.setSerialNumber("SN2");
//        equipmentBO2.setZoneName("SourceZone");
//        equipmentBO2.setSpaceName("SourceSpace");
//        equipmentBO2.setLocalityName("SourceLocality");
//
//        // Setup EquipmentToTransfer
//        equipmentToTransfer1 = new EquipmentToTransfer();
//        equipmentToTransfer1.setReferenceName("Ref1");
//        equipmentToTransfer1.setSerialNumber("SN1");
//        equipmentToTransfer1.setSourceZoneName("SourceZone");
//        equipmentToTransfer1.setSourceSpaceName("SourceSpace");
//
//        equipmentToTransfer2 = new EquipmentToTransfer();
//        equipmentToTransfer2.setReferenceName("Ref2");
//        equipmentToTransfer2.setSerialNumber("SN2");
//        equipmentToTransfer2.setSourceZoneName("SourceZone");
//        equipmentToTransfer2.setSourceSpaceName("SourceSpace");
//
//        // Setup EquipmentTransferBO
//        transferBO = new EquipmentTransferBO();
//        transferBO.setTargetZoneName("TargetZone");
//        transferBO.setTargetSpaceName("TargetSpace");
//        transferBO.setTargetLocalityName("TargetLocality");
//        transferBO.setDetails("Test transfer");
//        Set<EquipmentToTransfer> equipmentsToTransfer = new HashSet<>();
//        equipmentsToTransfer.add(equipmentToTransfer1);
//        transferBO.setEquipments(equipmentsToTransfer);
//
//        // Setup EquipmentTransfer (résultat de sauvegarde)
//        savedTransfer = new EquipmentTransfer();
//        savedTransfer.setId(1L);
//        savedTransfer.setEquipment(equipment1);
//        savedTransfer.setFromZone("SourceZone");
//        savedTransfer.setFromSpace("SourceSpace");
//        savedTransfer.setFromLocality("SourceLocality");
//        savedTransfer.setToZone("TargetZone");
//        savedTransfer.setToSpace("TargetSpace");
//        savedTransfer.setToLocality("TargetLocality");
//        savedTransfer.setTransferDate(LocalDateTime.now());
//        savedTransfer.setDetails("Test transfer");
//
//        // Setup EquipmentTransferBO (résultat de mapping)
//        savedTransferBO = new EquipmentTransferBO();
//        savedTransferBO.setTargetZoneName("TargetZone");
//        savedTransferBO.setTargetSpaceName("TargetSpace");
//        savedTransferBO.setTargetLocalityName("TargetLocality");
//        savedTransferBO.setTransferDate(LocalDateTime.now());
//        savedTransferBO.setDetails("Test transfer");
//        Set<EquipmentToTransfer> equipmentSet = new HashSet<>();
//        equipmentSet.add(equipmentToTransfer1);
//        savedTransferBO.setEquipments(equipmentSet);
//    }
//
//    @Test
//    void processTransfer_Success() {
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality"))
//                .thenReturn(Optional.of(targetZone));
//
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("SN1", "Ref1"))
//                .thenReturn(Optional.of(equipment1));
//
//        when(equipmentMapper.entityToBO(equipment1)).thenReturn(equipmentBO1);
//        when(equipmentMapper.boToEntity(equipmentBO1)).thenReturn(equipment1);
//
//        when(equipmentTransferRepo.save(any(EquipmentTransfer.class))).thenReturn(savedTransfer);
//        when(transferMapper.entityToBO(savedTransfer)).thenReturn(savedTransferBO);
//
//        Set<EquipmentTransferBO> result = equipmentTransferService.processTransfer(transferBO);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("TargetZone", result.iterator().next().getTargetZoneName());
//
//        verify(zoneRepository, times(2)).findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality");
//        verify(equipmentRepo, times(2)).findByIdSerialNumberAndIdReferenceName("SN1", "Ref1");
//        verify(equipmentMapper).entityToBO(equipment1);
//        verify(equipmentTransferRepo).save(any(EquipmentTransfer.class));
//        verify(equipmentRepo).save(equipment1);
//    }
//
//    @Test
//    void processTransfer_TargetZoneNotFound_ThrowsException() {
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ZoneNotFoundException.class, () -> {
//            equipmentTransferService.processTransfer(transferBO);
//        });
//
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality");
//        verify(equipmentRepo, never()).findByIdSerialNumberAndIdReferenceName(anyString(), anyString());
//    }
//
//    @Test
//    void processTransfer_EquipmentNotFound_ThrowsException() {
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality"))
//                .thenReturn(Optional.of(targetZone));
//
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("SN1", "Ref1"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(EquipmentNotFoundException.class, () -> {
//            equipmentTransferService.processTransfer(transferBO);
//        });
//
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality");
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("SN1", "Ref1");
//        verify(equipmentTransferRepo, never()).save(any(EquipmentTransfer.class));
//    }
//
//    @Test
//    void processTransfer_LocationMismatch_ThrowsException() {
//        EquipmentBO wrongLocationBO = new EquipmentBO();
//        wrongLocationBO.setReferenceName("Ref1");
//        wrongLocationBO.setSerialNumber("SN1");
//        wrongLocationBO.setZoneName("WrongZone");
//        wrongLocationBO.setSpaceName("WrongSpace");
//        wrongLocationBO.setLocalityName("SourceLocality");
//
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality"))
//                .thenReturn(Optional.of(targetZone));
//
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("SN1", "Ref1"))
//                .thenReturn(Optional.of(equipment1));
//
//        when(equipmentMapper.entityToBO(equipment1)).thenReturn(wrongLocationBO);
//
//        assertThrows(EquipmentLocationMismatchException.class, () -> {
//            equipmentTransferService.processTransfer(transferBO);
//        });
//
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality");
//        verify(equipmentRepo, times(2)).findByIdSerialNumberAndIdReferenceName("SN1", "Ref1");
//        verify(equipmentMapper).entityToBO(equipment1);
//        verify(equipmentTransferRepo, never()).save(any(EquipmentTransfer.class));
//    }
//
//    @Test
//    void processTransfer_MultipleLocalities_ThrowsException() {
//        // Ajouter un deuxième équipement d'une localité différente
//        Equipment equipmentFromDifferentLocality = new Equipment();
//        EquipmentPK pkDiff = new EquipmentPK();
//        pkDiff.setReferenceName("Ref3");
//        pkDiff.setSerialNumber("SN3");
//        equipmentFromDifferentLocality.setId(pkDiff);
//
//        Locality differentLocality = new Locality();
//        differentLocality.setName("DifferentLocality");
//
//        Space differentSpace = new Space();
//        differentSpace.setName("DifferentSpace");
//        differentSpace.setLocality(differentLocality);
//
//        Zone differentZone = new Zone();
//        differentZone.setName("DifferentZone");
//        differentZone.setSpace(differentSpace);
//
//        equipmentFromDifferentLocality.setZone(differentZone);
//
//        // Créer un transferBO avec plusieurs équipements
//        EquipmentToTransfer equipmentToTransfer3 = new EquipmentToTransfer();
//        equipmentToTransfer3.setReferenceName("Ref3");
//        equipmentToTransfer3.setSerialNumber("SN3");
//        equipmentToTransfer3.setSourceZoneName("DifferentZone");
//        equipmentToTransfer3.setSourceSpaceName("DifferentSpace");
//
//        Set<EquipmentToTransfer> multipleEquipments = new HashSet<>();
//        multipleEquipments.add(equipmentToTransfer1);
//        multipleEquipments.add(equipmentToTransfer3);
//
//        EquipmentTransferBO multiTransferBO = new EquipmentTransferBO();
//        multiTransferBO.setEquipments(multipleEquipments);
//        multiTransferBO.setTargetZoneName("TargetZone");
//        multiTransferBO.setTargetSpaceName("TargetSpace");
//        multiTransferBO.setTargetLocalityName("TargetLocality");
//
//        when(zoneRepository.findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality"))
//                .thenReturn(Optional.of(targetZone));
//
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("SN1", "Ref1"))
//                .thenReturn(Optional.of(equipment1));
//
//        when(equipmentRepo.findByIdSerialNumberAndIdReferenceName("SN3", "Ref3"))
//                .thenReturn(Optional.of(equipmentFromDifferentLocality));
//
//        assertThrows(TransferValidationException.class, () -> {
//            equipmentTransferService.processTransfer(multiTransferBO);
//        });
//
//        verify(zoneRepository).findByNameAndSpaceNameAndSpaceLocalityName(
//                "TargetZone", "TargetSpace", "TargetLocality");
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("SN1", "Ref1");
//        verify(equipmentRepo).findByIdSerialNumberAndIdReferenceName("SN3", "Ref3");
//    }
//
//    @Test
//    void getTransferHistory_Success() {
//        List<EquipmentTransfer> transferHistory = List.of(savedTransfer);
//        when(equipmentTransferRepo.findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber("Ref1", "SN1"))
//                .thenReturn(transferHistory);
//        when(transferMapper.entityToBO(savedTransfer)).thenReturn(savedTransferBO);
//
//        Set<EquipmentTransferBO> result = equipmentTransferService.getTransferHistory("Ref1", "SN1");
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("TargetZone", result.iterator().next().getTargetZoneName());
//
//        verify(equipmentTransferRepo).findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber("Ref1", "SN1");
//        verify(transferMapper).entityToBO(savedTransfer);
//    }
//
//    @Test
//    void getTransferHistory_EmptyHistory() {
//        when(equipmentTransferRepo.findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber("Ref1", "SN1"))
//                .thenReturn(List.of());
//
//        Set<EquipmentTransferBO> result = equipmentTransferService.getTransferHistory("Ref1", "SN1");
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//
//        verify(equipmentTransferRepo).findByEquipmentIdReferenceNameAndEquipmentIdSerialNumber("Ref1", "SN1");
//    }
//}
