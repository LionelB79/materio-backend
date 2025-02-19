package com.materio.materio_backend.dto.Locality;
import com.materio.materio_backend.dto.Room.RoomMapper;
import com.materio.materio_backend.jpa.entity.Locality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LocalityMapper {

    @Autowired
    RoomMapper roomMapper;
    public Locality BOToEntity(LocalityBO request) {
        Locality locality = new Locality();
        locality.setName(request.getName());
        locality.setAddress(request.getAddress());
        locality.setCp(request.getCp());
        locality.setCity(request.getCity());
        return locality;
    }

    public LocalityVO EntityToVO(Locality locality) {
        LocalityVO response = new LocalityVO();
        response.setName(locality.getName());
        response.setAddress(locality.getAddress());
        response.setCp(locality.getCp());
        response.setCity(locality.getCity());

        if (locality.getRooms() != null) {
            response.setRooms(locality.getRooms().stream()
                    .map(room -> roomMapper.EntityToVO(room))
                    .collect(Collectors.toList()));
        }
        return response;
    }
}
