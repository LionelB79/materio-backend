package com.materio.materio_backend.dto.Locality;

import com.materio.materio_backend.dto.Room.RoomVO;
import lombok.Data;

import java.util.List;

@Data
public class LocalityVO {
    private String name;
    private String address;
    private Integer cp;
    private String city;
    private List<RoomVO> rooms;
}
