package com.onofftaxi.backend.repositories;

import com.onofftaxi.backend.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place,Long> {

    List<Place> findAllById(Iterable<Long> iterable);

    List<Place> findAllByDistrict(String district);
}
