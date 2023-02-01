package com.onofftaxi.backend.repositories;

import com.onofftaxi.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User where login like %:name%")
    List<User> searchByName(@Param("name")String name);

    @Query("from User where login like %:email%")
    Optional<User> findByEmail(@Param("email")String email);

//    @Query(value = "select u from drivers d join users u on d.user_id = u.id where u.login=?1 or d.email = ?1 or d.phone=?1 ",nativeQuery = false)
//    Optional<User> findByEmailOrLoginOrPhone(@Param("email") String email);


    User getUserByLogin(String login);
    User getUserByLoginAndPassword(String login, String password);
    User getUserByPassword(String password);

    Optional<User> findByLogin(String email);
}
