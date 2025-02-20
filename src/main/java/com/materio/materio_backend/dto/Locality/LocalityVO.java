package com.materio.materio_backend.dto.Locality;

import com.materio.materio_backend.dto.Space.SpaceVO;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class LocalityVO {
    private String name;
    private String address;
    private Integer cp;
    private String city;
    private Set<SpaceVO> spaces = new HashSet<>();
}

