package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
