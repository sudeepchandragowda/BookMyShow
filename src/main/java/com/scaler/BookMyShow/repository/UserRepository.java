package com.scaler.BookMyShow.repository;

import com.scaler.BookMyShow.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <Users, Long> {
    @Override
    Optional<Users> findById(Long userId);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByEmailName(String name, String email);
    @Override
    Users save (Users user);

}
