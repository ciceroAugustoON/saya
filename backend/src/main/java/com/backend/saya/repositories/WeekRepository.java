package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.Week;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long>{

}
