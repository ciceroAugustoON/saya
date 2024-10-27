package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.Relatory;

@Repository
public interface RelatoryRepository extends JpaRepository<Relatory, Long>{

}
