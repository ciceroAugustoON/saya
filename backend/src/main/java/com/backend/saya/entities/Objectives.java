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
	private Integer dailySpendedHours, metaReduction;
	@OneToMany
	private List<Habit> habitsHad, desiredHabits = new ArrayList<Habit>();

	
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
	
	public List<Habit> getHabitsHad() {
		return habitsHad;
	}

	public void addHabitHad(Habit habit) {
		habitsHad.add(habit);
	}
	
	public void addAllHabitsHad(Habit...habits) {
		this.habitsHad.addAll(List.of(habits));
	}
	
	public void removeHabitHad(Habit habit) {
		habitsHad.remove(habit);
	}

	public List<Habit> getDesiredHabits() {
		return desiredHabits;
	}

	public void addDesiredHabit(Habit habit) {
		desiredHabits.add(habit);
	}

	public void addAllDesiredHabits(Habit...habits) {
		this.desiredHabits.addAll(List.of(habits));
	}

	public void removeDesiredHabit(Habit habit) {
		desiredHabits.remove(habit);
	}

	public List<Habit> getHabits() {
		var habits = new ArrayList<Habit>();
		if (getHabitsHad() != null) {
			habits.addAll(getHabitsHad());
		}
		if (getDesiredHabits() != null) {
			habits.addAll(getDesiredHabits());
		}
		return habits;
	}
}
