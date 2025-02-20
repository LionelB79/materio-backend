package com.materio.materio_backend.dto.Zone;

import com.materio.materio_backend.dto.Equipment.EquipmentBO;
import com.materio.materio_backend.dto.Equipment.EquipmentMapper;
import com.materio.materio_backend.jpa.entity.Equipment;
import com.materio.materio_backend.jpa.entity.Zone;
import com.materio.materio_backend.jpa.repository.SpaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
    @RequiredArgsConstructor
    public class ZoneMapper {
    @Autowired
        private EquipmentMapper equipmentMapper;

        /**
         * Convertit un BO en entité Zone
         * Cette méthode est utilisée lors de la création/mise à jour d'une zone
         * Note: La relation avec Space est gérée par le service
         */
        public Zone boToEntity(ZoneBO bo) {
            if (bo == null) return null;

            Zone entity = new Zone();

            // Attributs simples
            entity.setName(bo.getName());
            entity.setDescription(bo.getDescription());

            // Conversion des équipements si présents
            if (bo.getEquipments() != null && !bo.getEquipments().isEmpty()) {
                Set<Equipment> equipments = bo.getEquipments().stream()
                        .map(equipmentMapper::boToEntity)
                        .collect(Collectors.toSet());
                entity.setEquipments(equipments);
            } else {
                entity.setEquipments(new HashSet<>());
            }

            // Note: Space sera défini par le service car il nécessite une recherche en base

            return entity;
        }

        /**
         * Convertit une entité Zone en BO
         * Cette méthode enrichit le BO avec les informations de navigation
         * (spaceName, localityName, etc.)
         */
        public ZoneBO entityToBO(Zone entity) {
            if (entity == null) return null;

            ZoneBO bo = new ZoneBO();

            // Attributs simples
            bo.setName(entity.getName());
            bo.setDescription(entity.getDescription());

            // Navigation information
            if (entity.getSpace() != null) {
                bo.setSpaceName(entity.getSpace().getName());
                if (entity.getSpace().getLocality() != null) {
                    bo.setLocalityName(entity.getSpace().getLocality().getName());
                }
            }

            // Conversion des équipements associés
            if (entity.getEquipments() != null) {
                Set<EquipmentBO> equipments = entity.getEquipments().stream()
                        .map(equipmentMapper::entityToBO)
                        .collect(Collectors.toSet());
                bo.setEquipments(equipments);
            }

            return bo;
        }

        /**
         * Convertit un BO en VO pour l'affichage
         * Le VO contient des informations supplémentaires pour la présentation
         */
        public ZoneVO boToVO(ZoneBO bo) {
            if (bo == null) return null;

            ZoneVO vo = new ZoneVO();

            // Copie des attributs communs
            vo.setName(bo.getName());
            vo.setDescription(bo.getDescription());
            vo.setSpaceName(bo.getSpaceName());
            vo.setLocalityName(bo.getLocalityName());
            vo.setZoneName(bo.getName()); // Le zoneName dans le VO est le même que le name

            // Conversion des équipements si présents
            if (bo.getEquipments() != null) {
                vo.setEquipments(new HashSet<>(bo.getEquipments())); // Les EquipmentBO sont les mêmes
            }

            return vo;
        }

        /**
         * Convertit un VO en BO
         * Utilisé principalement pour les opérations depuis l'interface utilisateur
         */
        public ZoneBO voToBO(ZoneVO vo) {
            if (vo == null) return null;

            ZoneBO bo = new ZoneBO();

            // Copie des attributs de base
            bo.setName(vo.getName());
            bo.setDescription(vo.getDescription());
            bo.setSpaceName(vo.getSpaceName());
            bo.setLocalityName(vo.getLocalityName());

            // Copie des équipements si présents
            if (vo.getEquipments() != null) {
                bo.setEquipments(new HashSet<>(vo.getEquipments()));
            }

            return bo;
        }

        /**
         * Met à jour une entité existante avec les données d'un BO
         * Utilisé pour les mises à jour partielles
         */
        public void updateEntityFromBO(Zone entity, ZoneBO bo) {
            if (entity == null || bo == null) return;

            // Mise à jour uniquement des champs modifiables
            entity.setName(bo.getName());
            entity.setDescription(bo.getDescription());

            // Note: Les relations (Space, Equipment) sont gérées par le service
        }
    }