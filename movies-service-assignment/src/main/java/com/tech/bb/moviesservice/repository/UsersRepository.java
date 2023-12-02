package com.tech.bb.moviesservice.repository;

import com.tech.bb.moviesservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByName(String userName);
    Optional<Users> findByApiKey(String apiKey);
}
