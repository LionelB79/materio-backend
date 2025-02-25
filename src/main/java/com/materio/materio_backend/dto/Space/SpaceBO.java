package com.materio.materio_backend.dto.Space;

import com.materio.materio_backend.dto.Zone.ZoneBO;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceBO {
    private Long id;
    private String name;
    private String localityName;
    private Set<ZoneBO> zones = new HashSet<>();
}
