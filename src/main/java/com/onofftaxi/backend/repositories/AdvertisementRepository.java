package com.onofftaxi.backend.repositories;

import com.onofftaxi.backend.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findById(Long id);
}
