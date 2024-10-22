package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.Habit;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long>{

}
