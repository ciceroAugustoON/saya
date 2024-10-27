package com.backend.saya.entities;

import java.io.Serializable;

import com.backend.saya.entities.enumeration.WeekDay;
import com.backend.saya.util.MathUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_week")
public class Week implements Serializable{
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private int[] hardTasksPerDay = new int[7];
	private int[] mediumTasksPerDay = new int[7];
	private int[] easyTasksPerDay = new int[7];
	
	public Week() {
		
	}

	public Week(Long id, int[] hardTasksPerDay, int[] mediumTasksPerDay,
			int[] easyTasksPerDay) {
		this.id = id;
		this.hardTasksPerDay = hardTasksPerDay;
		this.mediumTasksPerDay = mediumTasksPerDay;
		this.easyTasksPerDay = easyTasksPerDay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int[] getHardTasksPerDay() {
		return hardTasksPerDay;
	}

	public void setHardTasksPerDay(WeekDay day, int value) {
		this.hardTasksPerDay[day.getDayValue()] = value;
	}

	public int[] getMediumTasksPerDay() {
		return mediumTasksPerDay;
	}

	public void setMediumTasksPerDay(WeekDay day, int value) {
		this.mediumTasksPerDay[day.getDayValue()] = value;
	}

	public int[] getEasyTasksPerDay() {
		return easyTasksPerDay;
	}

	public void setEasyTasksPerDay(WeekDay day, int value) {
		this.easyTasksPerDay[day.getDayValue()] = value;
	}
	
	public int getValuePoints() {
		return MathUtils.sumIntegerArray(easyTasksPerDay) * 2 + MathUtils.sumIntegerArray(mediumTasksPerDay) * 5 + MathUtils.sumIntegerArray(hardTasksPerDay) * 10;
	}
	
}
