package com.backend.saya.entities;

import java.io.Serializable;
import java.util.Objects;

import com.backend.saya.entities.enumeration.Difficulty;
import com.backend.saya.entities.enumeration.WeekDay;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_relatory")
public class Relatory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// allDailyTasksDone to be implemented
	private Integer points = 0;
	private Integer offensive = 0;
	private Integer tasksQuantity;
	private boolean firstTaskOfDay;

	public Relatory() {
	}

	public Relatory(Long id, Integer points, Integer offensive) {
		this.id = id;
		this.points = points;
		this.offensive = offensive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPoints() {
		return points;
	}

	public void addPoints(Integer points) {
		points += points;
	}

	public Integer getOffensive() {
		return offensive;
	}

	public void increaseOffensive() {
		this.offensive++;
	}

	public void loseOffencive() {
		this.offensive = 0;
	}

	public boolean isFirstTaskOfDay() {
		return firstTaskOfDay;
	}

	public void setFirstTaskOfDay(boolean firstTaskOfDay) {
		this.firstTaskOfDay = firstTaskOfDay;
	}

	public Integer getTasksQuantity() {
		return tasksQuantity;
	}

	public void setTasksQuantity(Integer tasksQuantity) {
		this.tasksQuantity = tasksQuantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relatory other = (Relatory) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Relatory{" +
				"offensive=" + offensive +
				", points=" + points +
				'}';
	}

}
