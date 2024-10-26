package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.Objectives;

@Repository
public interface ObjectivesRepository extends JpaRepository<Objectives, Long>{

}
