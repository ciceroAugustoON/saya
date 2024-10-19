package com.backend.saya.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Habit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer todayMinutes;
	private Integer monthMinutes;
	private List<Task> tasks = new ArrayList<>();
	
	public Habit() {
		
	}

	public Habit(String name, Integer todayMinutes, Integer monthMinutes, List<Task> tasks) {
		this.name = name;
		this.todayMinutes = todayMinutes;
		this.monthMinutes = monthMinutes;
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTodayMinutes() {
		return todayMinutes;
	}

	public void setTodayMinutes(Integer todayMinutes) {
		this.todayMinutes = todayMinutes;
	}

	public Integer getMonthMinutes() {
		return monthMinutes;
	}

	public void setMonthMinutes(Integer monthMinutes) {
		this.monthMinutes = monthMinutes;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
	}
}
