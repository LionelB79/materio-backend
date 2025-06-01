//package com.materio.materio_backend.service;
//
//
//
//
//
//import static org.junit.jupiter.api.Assertions.*;
//        import static org.mockito.Mockito.*;
//
//        import com.materio.materio_backend.business.exception.locality.DuplicateLocalityException;
//import com.materio.materio_backend.business.exception.locality.LocalityNotFoundException;
//import com.materio.materio_backend.business.service.EntityValidationService;
//import com.materio.materio_backend.business.service.impl.LocalityServiceImpl;
//import com.materio.materio_backend.dto.Locality.LocalityBO;
//import com.materio.materio_backend.dto.Locality.LocalityMapper;
//import com.materio.materio_backend.jpa.entity.Locality;
//import com.materio.materio_backend.jpa.repository.LocalityRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@ExtendWith(MockitoExtension.class)
//public class LocalityServiceImplTest {
//
//    @Mock
//    private LocalityRepository localityRepo;
//
//    @Mock
//    private LocalityMapper localityMapper;
//
//    @Mock
//    private EntityValidationService validationService;
//
//    @InjectMocks
//    private LocalityServiceImpl localityService;
//
//    private LocalityBO localityBO;
//    private Locality localityEntity;
//
//    @BeforeEach
//    void initBeforeEach() {
//
//        localityBO = new LocalityBO();
//        localityBO.setName("TestLocality");
//        localityBO.setAddress("123 Test Street");
//        localityBO.setCp(12345);
//        localityBO.setCity("Test City");
//        localityBO.setSpaces(new HashSet<>());
//
//
//        localityEntity = new Locality();
//        localityEntity.setName("TestLocality");
//        localityEntity.setAddress("123 Test Street");
//        localityEntity.setCp(12345);
//        localityEntity.setCity("Test City");
//        localityEntity.setSpaces(new HashSet<>());
//    }
//
//    @Test
//    void createLocality_Success() {
//
//        when(localityRepo.findByName("TestLocality")).thenReturn(Optional.empty());
//        when(localityMapper.boToEntity(localityBO)).thenReturn(localityEntity);
//        when(localityRepo.save(localityEntity)).thenReturn(localityEntity);
//        when(localityMapper.entityToBO(localityEntity)).thenReturn(localityBO);
//
//
//        LocalityBO result = localityService.createLocality(localityBO);
//
//        assertNotNull(result);
//        assertEquals("TestLocality", result.getName());
//        assertEquals("123 Test Street", result.getAddress());
//        assertEquals(12345, result.getCp());
//        assertEquals("Test City", result.getCity());
//
//
//        verify(localityRepo).findByName("TestLocality");
//        verify(localityMapper).boToEntity(localityBO);
//        verify(localityRepo).save(localityEntity);
//        verify(localityMapper).entityToBO(localityEntity);
//    }
//
//    @Test
//    void createLocality_DuplicateName_ThrowsException() {
//        when(localityRepo.findByName("TestLocality")).thenReturn(Optional.of(localityEntity));
//
//        assertThrows(DuplicateLocalityException.class, () -> {
//            localityService.createLocality(localityBO);
//        });
//
//        verify(localityRepo).findByName("TestLocality");
//        verify(localityRepo, never()).save(any(Locality.class));
//    }
//
//    @Test
//    void getLocality_Success() {
//        when(localityRepo.findByName("TestLocality")).thenReturn(Optional.of(localityEntity));
//        when(localityMapper.entityToBO(localityEntity)).thenReturn(localityBO);
//
//        LocalityBO result = localityService.getLocality("TestLocality");
//
//        assertNotNull(result);
//        assertEquals("TestLocality", result.getName());
//
//        verify(localityRepo).findByName("TestLocality");
//        verify(localityMapper).entityToBO(localityEntity);
//    }
//
//    @Test
//    void getLocality_NotFound_ThrowsException() {
//        when(localityRepo.findByName("NonExistentLocality")).thenReturn(Optional.empty());
//
//        assertThrows(LocalityNotFoundException.class, () -> {
//            localityService.getLocality("NonExistentLocality");
//        });
//
//        verify(localityRepo).findByName("NonExistentLocality");
//    }
//
//    @Test
//    void getAllLocalities_Success() {
//        List<Locality> entities = List.of(localityEntity);
//
//        when(localityRepo.findAll()).thenReturn(entities);
//        when(localityMapper.entityToBO(localityEntity)).thenReturn(localityBO);
//
//        Set<LocalityBO> results = localityService.getAllLocalities();
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//
//        verify(localityRepo).findAll();
//        verify(localityMapper).entityToBO(localityEntity);
//    }
//
//    @Test
//    void updateLocality_Success() {
//        LocalityBO updatedBO = new LocalityBO();
//        updatedBO.setName("TestLocality"); // Même nom
//        updatedBO.setAddress("456 New Street");
//        updatedBO.setCp(54321);
//        updatedBO.setCity("New City");
//
//        when(localityRepo.findByName("TestLocality")).thenReturn(Optional.of(localityEntity));
//        when(localityRepo.save(localityEntity)).thenReturn(localityEntity);
//        when(localityMapper.entityToBO(localityEntity)).thenReturn(updatedBO);
//
//        LocalityBO result = localityService.updateLocality("TestLocality", updatedBO);
//
//        assertNotNull(result);
//        assertEquals("TestLocality", result.getName());
//        assertEquals("456 New Street", result.getAddress());
//        assertEquals(54321, result.getCp());
//        assertEquals("New City", result.getCity());
//
//        verify(localityRepo).findByName("TestLocality");
//        verify(localityMapper).updateEntityFromBO(localityEntity, updatedBO);
//        verify(localityRepo).save(localityEntity);
//        verify(localityMapper).entityToBO(localityEntity);
//    }
//
//    @Test
//    void deleteLocality_Success() {
//        when(localityRepo.findByName("TestLocality")).thenReturn(Optional.of(localityEntity));
//        when(validationService.isLocalityEmpty("TestLocality")).thenReturn(true);
//
//        localityService.deleteLocality("TestLocality");
//
//        verify(localityRepo).findByName("TestLocality");
//        verify(validationService).isLocalityEmpty("TestLocality");
//        verify(localityRepo).delete(localityEntity);
//    }
//}