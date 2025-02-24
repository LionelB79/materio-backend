package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.Transfer.EquipmentTransferBO;

import java.util.List;
import java.util.Set;

public interface EquipmentTransferService {
    /**
     * Traite le transfert d'un équipement
     * @param transferBO Les informations du transfert à effectuer
     * @return Le transfert effectué
     */
    Set<EquipmentTransferBO> processTransfer(EquipmentTransferBO transferBO);

    /**
     * Récupère l'historique des transferts d'un équipement
     * @param referenceName La référence de l'équipement
     * @param serialNumber Le numéro de série de l'équipement
     * @return La liste des transferts de l'équipement
     */
    Set<EquipmentTransferBO> getTransferHistory(String referenceName, String serialNumber);
}