package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.exception.equipment.EquipmentLocationMismatchException;
import com.materio.materio_backend.business.exception.equipment.EquipmentNotFoundException;
import com.materio.materio_backend.business.exception.transfer.TransferValidationException;
import com.materio.materio_backend.business.exception.zone.ZoneNotFoundException;
import com.materio.materio_backend.business.service.*;
import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.dto.Transfer.*;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.EquipmentTransfer;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.EquipmentRepository;
import com.materio.materio_backend.jpa.repository.EquipmentTransferRepository;
import com.materio.materio_backend.jpa.repository.ZoneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class EquipmentTransferServiceImpl implements EquipmentTransferService {

    @Autowired
    private EquipmentRepository equipmentRepo;

    @Autowired
    private EquipmentTransferRepository equipmentTransferRepo;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private EquipmentTransferMapper transferMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<EquipmentTransferBO> processTransfer(EquipmentTransferBO transferBO) {
        // Vérifier que la zone cible existe
        Zone targetZone = zoneRepository.findById(transferBO.getTargetZoneId())
                .orElseThrow(() -> new ZoneNotFoundException("ID: " + transferBO.getTargetZoneId()));

        Set<EquipmentTransferBO> results = new HashSet<>();
        Set<Equipment> equipmentsToTransfer = new HashSet<>();

        // Récupérer tous les équipements par ID
        for (Long equipmentId : transferBO.getEquipmentIds()) {
            Equipment equipment = equipmentRepo.findById(equipmentId)
                    .orElseThrow(() -> new EquipmentNotFoundException("ID: " + equipmentId));
            equipmentsToTransfer.add(equipment);
        }

        // Vérification que tous les équipements sont de la même localité source
        validateSameSourceLocality(equipmentsToTransfer);

        for (Equipment equipment : equipmentsToTransfer) {
            // Créer et sauvegarder le transfert
            EquipmentTransfer transfer = createTransferEntity(equipment, targetZone, transferBO);
            EquipmentTransfer savedTransfer = equipmentTransferRepo.save(transfer);

            // Mettre à jour la localisation de l'équipement
            equipment.setZone(targetZone);
            equipmentRepo.save(equipment);

            // Pour la réponse, définir les IDs correctement
            EquipmentTransferBO responseBO = transferMapper.entityToBO(savedTransfer);
            responseBO.setTargetZoneId(transferBO.getTargetZoneId());
            results.add(responseBO);
        }

        return results;
    }

    @Override
    public Set<TransferHistoryVO> getTransferHistory(Long equipmentId) {
        // Utilisation de la vue pour récupérer l'historique complet
        String queryStr = "SELECT t.id, t.equipment_id, e.reference_name, e.serial_number, " +
                "t.from_zone_id, fz.name, fs.name, fl.name, " +
                "t.to_zone_id, tz.name, ts.name, tl.name, " +
                "t.transfer_date, t.transfer_details " +
                "FROM t_transfer t " +
                "JOIN t_equipment e ON t.equipment_id = e.id " +
                "LEFT JOIN t_zone fz ON t.from_zone_id = fz.id " +
                "LEFT JOIN t_space fs ON fz.space_id = fs.id " +
                "LEFT JOIN t_locality fl ON fs.locality_id = fl.id " +
                "LEFT JOIN t_zone tz ON t.to_zone_id = tz.id " +
                "LEFT JOIN t_space ts ON tz.space_id = ts.id " +
                "LEFT JOIN t_locality tl ON ts.locality_id = tl.id " +
                "WHERE t.equipment_id = :equipmentId";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter("equipmentId", equipmentId);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        Set<TransferHistoryVO> historySet = new HashSet<>();

        for (Object[] result : results) {
            TransferHistoryVO vo = new TransferHistoryVO();
            int i = 0;

            vo.setId(((Number) result[i++]).longValue());
            vo.setEquipmentId(((Number) result[i++]).longValue());
            vo.setEquipmentReference((String) result[i++]);
            vo.setEquipmentSerialNumber((String) result[i++]);

            vo.setFromZoneId(result[i] != null ? ((Number) result[i]).longValue() : null);
            i++;
            vo.setFromZone((String) result[i++]);
            vo.setFromSpace((String) result[i++]);
            vo.setFromLocality((String) result[i++]);

            vo.setToZoneId(result[i] != null ? ((Number) result[i]).longValue() : null);
            i++;
            vo.setToZone((String) result[i++]);
            vo.setToSpace((String) result[i++]);
            vo.setToLocality((String) result[i++]);

            if (null != result[i]) {
                java.sql.Timestamp timestamp = (java.sql.Timestamp) result[i];
                vo.setTransferDate(timestamp.toLocalDateTime());
            }
            i++;
            vo.setDetails((String) result[i]);

            historySet.add(vo);
        }

        return historySet;
    }

    private EquipmentTransfer createTransferEntity(Equipment equipment, Zone targetZone, EquipmentTransferBO transferBO) {
        EquipmentTransfer transfer = new EquipmentTransfer();
        transfer.setEquipment(equipment);
        transfer.setTransferDate(LocalDateTime.now());

        // Stocker uniquement les IDs des zones
        transfer.setFromZoneId(equipment.getZone().getId());
        transfer.setToZoneId(targetZone.getId());

        transfer.setDetails(transferBO.getDetails());

        return transfer;
    }

    private void validateSameSourceLocality(Set<Equipment> equipments) {
        Set<Long> sourceLocalityIds = equipments.stream()
                .map(eq -> eq.getZone().getSpace().getLocality().getId())
                .collect(Collectors.toSet());

        if (sourceLocalityIds.size() > 1) {
            throw new TransferValidationException("Les équipements doivent provenir de la même localité");
        }
    }
}