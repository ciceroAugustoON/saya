package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    public Optional<User> findByUsername(String username);
}
