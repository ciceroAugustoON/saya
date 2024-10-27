package com.backend.saya.entities;

import java.io.Serializable;
import java.util.Objects;

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
	private Integer lastWeekPoints;
	// allDailyTasksDone to be implemented
	private Integer easyTasks;
	private Integer mediumTasks;
	private Integer hardTasks;
	private Integer weekDay;
	
	public Relatory() {
		
	}

	public Relatory(Long id, Integer lastWeekPoints, Integer easyTasks, Integer mediumTasks, Integer hardTasks,
			Integer weekDay) {
		this.id = id;
		this.lastWeekPoints = lastWeekPoints;
		this.easyTasks = easyTasks;
		this.mediumTasks = mediumTasks;
		this.hardTasks = hardTasks;
		this.weekDay = weekDay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLastWeekPoints() {
		return lastWeekPoints;
	}

	public void setLastWeekPoints(Integer lastWeekPoints) {
		this.lastWeekPoints = lastWeekPoints;
	}

	public Integer getEasyTasks() {
		return easyTasks;
	}

	public void setEasyTasks(Integer easyTasks) {
		this.easyTasks = easyTasks;
	}

	public Integer getMediumTasks() {
		return mediumTasks;
	}

	public void setMediumTasks(Integer mediumTasks) {
		this.mediumTasks = mediumTasks;
	}

	public Integer getHardTasks() {
		return hardTasks;
	}

	public void setHardTasks(Integer hardTasks) {
		this.hardTasks = hardTasks;
	}

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
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
		return "Relatory [id=" + id + ", lastWeekPoints=" + lastWeekPoints + ", easyTasks=" + easyTasks
				+ ", mediumTasks=" + mediumTasks + ", hardTasks=" + hardTasks + ", weekDay=" + weekDay + "]";
	}
}
