package com.materio.materio_backend.dto.Transfer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferHistoryVO {
    private Long id;
    private Long equipmentId;
    private String equipmentReference;
    private String equipmentSerialNumber;

    // Source
    private Long fromZoneId;
    private String fromZone;
    private String fromSpace;
    private String fromLocality;

    // Destination
    private Long toZoneId;
    private String toZone;
    private String toSpace;
    private String toLocality;

    private LocalDateTime transferDate;
    private String details;
}
