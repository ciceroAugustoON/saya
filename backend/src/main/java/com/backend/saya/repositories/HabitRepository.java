package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.Habit;

import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long>{

    public Optional<Habit> findByName(String name);
}
