//package com.materio.materio_backend.service;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.materio.materio_backend.business.exception.reference.InvalidQuantityException;
//import com.materio.materio_backend.business.exception.reference.ReferenceNotFoundException;
//import com.materio.materio_backend.business.service.impl.EquipmentRefServiceImpl;
//import com.materio.materio_backend.jpa.entity.EquipmentRef;
//import com.materio.materio_backend.jpa.repository.EquipmentRefRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class EquipmentRefServiceImplTest {
//
//    @Mock
//    private EquipmentRefRepository equipmentRefRepo;
//
//    @InjectMocks
//    private EquipmentRefServiceImpl equipmentRefService;
//
//    private EquipmentRef existingRef;
//    private final String REFERENCE_NAME = "TestReference";
//
//    @BeforeEach
//    void initBeforeEach() {
//        // Préparation d'une référence existante
//        existingRef = new EquipmentRef();
//        existingRef.setId(1L);
//        existingRef.setName(REFERENCE_NAME);
//        existingRef.setQuantity(5);
//    }
//
//    @Test
//    void getOrCreateReference_ExistingReference_IncrementsQuantity() {
//        when(equipmentRefRepo.findByName(REFERENCE_NAME)).thenReturn(Optional.of(existingRef));
//        when(equipmentRefRepo.save(existingRef)).thenReturn(existingRef);
//
//        EquipmentRef result = equipmentRefService.getOrCreateReference(REFERENCE_NAME);
//
//        assertNotNull(result);
//        assertEquals(REFERENCE_NAME, result.getName());
//        assertEquals(6, result.getQuantity()); // Quantité incrémentée de 5 à 6
//
//        verify(equipmentRefRepo).findByName(REFERENCE_NAME);
//        verify(equipmentRefRepo).save(existingRef);
//    }
//
//    @Test
//    void getOrCreateReference_NewReference_CreatesNewEntry() {
//        when(equipmentRefRepo.findByName(REFERENCE_NAME)).thenReturn(Optional.empty());
//
//        EquipmentRef newRef = new EquipmentRef();
//        newRef.setName(REFERENCE_NAME);
//        newRef.setQuantity(1);
//
//        when(equipmentRefRepo.save(any(EquipmentRef.class))).thenAnswer(invocation -> {
//            EquipmentRef savedRef = invocation.getArgument(0);
//            savedRef.setId(2L);
//            return savedRef;
//        });
//
//        EquipmentRef result = equipmentRefService.getOrCreateReference(REFERENCE_NAME);
//
//        assertNotNull(result);
//        assertEquals(REFERENCE_NAME, result.getName());
//        assertEquals(1, result.getQuantity());
//        assertNotNull(result.getId());
//
//        verify(equipmentRefRepo).findByName(REFERENCE_NAME);
//        verify(equipmentRefRepo).save(any(EquipmentRef.class));
//    }
//
//    @Test
//    void getReference_ExistingReference_ReturnsReference() {
//        when(equipmentRefRepo.findByName(REFERENCE_NAME)).thenReturn(Optional.of(existingRef));
//
//        EquipmentRef result = equipmentRefService.getReference(REFERENCE_NAME);
//
//        assertNotNull(result);
//        assertEquals(REFERENCE_NAME, result.getName());
//        assertEquals(5, result.getQuantity());
//
//        verify(equipmentRefRepo).findByName(REFERENCE_NAME);
//    }
//
//    @Test
//    void getReference_NonExistentReference_ThrowsException() {
//        when(equipmentRefRepo.findByName("NonExistentRef")).thenReturn(Optional.empty());
//
//        assertThrows(ReferenceNotFoundException.class, () -> {
//            equipmentRefService.getReference("NonExistentRef");
//        });
//
//        verify(equipmentRefRepo).findByName("NonExistentRef");
//    }
//
//    @Test
//    void decrementQuantity_ValidQuantity_Decrements() {
//        when(equipmentRefRepo.findByName(REFERENCE_NAME)).thenReturn(Optional.of(existingRef));
//        when(equipmentRefRepo.save(existingRef)).thenReturn(existingRef);
//
//        equipmentRefService.decrementQuantity(REFERENCE_NAME);
//
//        assertEquals(4, existingRef.getQuantity()); // Quantité décrémentée de 5 à 4
//
//        verify(equipmentRefRepo).findByName(REFERENCE_NAME);
//        verify(equipmentRefRepo).save(existingRef);
//    }
//
//    @Test
//    void decrementQuantity_ZeroQuantity_ThrowsException() {
//        EquipmentRef zeroQuantityRef = new EquipmentRef();
//        zeroQuantityRef.setId(3L);
//        zeroQuantityRef.setName("ZeroQuantityRef");
//        zeroQuantityRef.setQuantity(0);
//
//        when(equipmentRefRepo.findByName("ZeroQuantityRef")).thenReturn(Optional.of(zeroQuantityRef));
//
//        assertThrows(InvalidQuantityException.class, () -> {
//            equipmentRefService.decrementQuantity("ZeroQuantityRef");
//        });
//
//        verify(equipmentRefRepo).findByName("ZeroQuantityRef");
//        verify(equipmentRefRepo, never()).save(any(EquipmentRef.class)); // save ne doit pas être appelé
//    }
//
//    @Test
//    void decrementQuantity_NegativeQuantity_ThrowsException() {
//        EquipmentRef negativeQuantityRef = new EquipmentRef();
//        negativeQuantityRef.setId(4L);
//        negativeQuantityRef.setName("NegativeQuantityRef");
//        negativeQuantityRef.setQuantity(-1);
//
//        when(equipmentRefRepo.findByName("NegativeQuantityRef")).thenReturn(Optional.of(negativeQuantityRef));
//
//        assertThrows(InvalidQuantityException.class, () -> {
//            equipmentRefService.decrementQuantity("NegativeQuantityRef");
//        });
//
//        verify(equipmentRefRepo).findByName("NegativeQuantityRef");
//        verify(equipmentRefRepo, never()).save(any(EquipmentRef.class)); // save ne doit pas être appelé
//    }
//
//    @Test
//    void decrementQuantity_NonExistentReference_ThrowsException() {
//        when(equipmentRefRepo.findByName("NonExistentRef")).thenReturn(Optional.empty());
//
//        assertThrows(ReferenceNotFoundException.class, () -> {
//            equipmentRefService.decrementQuantity("NonExistentRef");
//        });
//
//        verify(equipmentRefRepo).findByName("NonExistentRef");
//    }
//}
