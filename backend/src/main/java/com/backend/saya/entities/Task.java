package com.backend.saya.entities;

import java.io.Serializable;

import com.backend.saya.entities.enumeration.Difficulty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Integer difficulty;
	private Integer timeSecs;
	@ManyToOne
	private Habit habit;
	
	public Task() {
	}
	
	public Task(Long id, String name, String description, Difficulty difficulty, Integer timeSecs) {
		this.id = id;
		this.name = name;
		this.description = description;
        this.timeSecs = timeSecs;
        setDifficulty(difficulty);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Difficulty getDifficulty() {
		return Difficulty.valueOf(difficulty);
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty.getCode();
	}

	public Habit getHabit() {
		return habit;
	}

	public void setHabit(Habit habit) {
		this.habit = habit;
	}

	public Integer getTimeSecs() {
		return timeSecs;
	}

	public void setTimeSecs(Integer timeSecs) {
		this.timeSecs = timeSecs;
	}
}
