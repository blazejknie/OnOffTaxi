package com.onofftaxi.backend.repositories;

import com.onofftaxi.backend.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    Driver findByLoginAndPassword(String login,String password);

    List<Driver> findAllByOrderByStatusDesc();

    List<Driver> findAllByPlace_DistrictOrderByStatusDesc(String district);

    Driver getByLogin(String login);

    @Query("from Driver d where d.email = ?1")
    Driver findByEmail(@Param("email") String email);

    Driver findByPhone(String email);

    Driver findByDisplayedName(String displayedName);


}
