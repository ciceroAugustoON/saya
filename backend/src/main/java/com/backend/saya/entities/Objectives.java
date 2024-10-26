package com.backend.saya.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_objectives")
public class Objectives implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer dailySpendedHours;
	private Integer metaReduction;
	@OneToMany
	private List<Habit> habits = new ArrayList<Habit>();
	@OneToOne
	private Relatory relatory;
	
	public Objectives() {
		
	}

	public Objectives(Integer dailySpendedHours, Integer metaReduction) {
		this.dailySpendedHours = dailySpendedHours;
		this.metaReduction = metaReduction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDailySpendedHours() {
		return dailySpendedHours;
	}

	public void setDailySpendedHours(Integer dailySpendedHours) {
		this.dailySpendedHours = dailySpendedHours;
	}

	public Integer getMetaReduction() {
		return metaReduction;
	}

	public void setMetaReduction(Integer metaReduction) {
		this.metaReduction = metaReduction;
	}
	
	public List<Habit> getHabits() {
		return habits;
	}

	public void addHabit(Habit habit) {
		habits.add(habit);
	}
	
	public void addAllHabits(Habit...habits) {
		this.habits.addAll(List.of(habits));
	}
	
	public void removeDailyTask(Habit habit) {
		habits.remove(habit);
	}

	public Relatory getRelatory() {
		return relatory;
	}

	public void setRelatory(Relatory relatory) {
		this.relatory = relatory;
	}
}
