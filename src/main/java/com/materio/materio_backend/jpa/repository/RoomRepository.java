package com.materio.materio_backend.jpa.repository;

import com.materio.materio_backend.jpa.entity.Locality;
import com.materio.materio_backend.jpa.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findByName(String name);
    Optional<Room> findByNameAndLocality_Name(String name, String localityName);

    List<Room> findByLocality_Name(String localityName);
}
