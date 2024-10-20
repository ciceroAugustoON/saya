package com.backend.saya.entities;

import java.io.Serializable;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_objectives")
public class Objectives implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer dailySpendedHours;
	private Integer metaReduction;
	@Transient
	private Map<Hobbies, Relatory> hobbies;
	
	public Objectives() {
		
	}

	public Objectives(Integer dailySpendedHours, Integer metaReduction, Map<Hobbies, Relatory> hobbies) {
		this.dailySpendedHours = dailySpendedHours;
		this.metaReduction = metaReduction;
		this.hobbies = hobbies;
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

	public Map<Hobbies, Relatory> getHobbies() {
		return hobbies;
	}

	public void setHobbies(Map<Hobbies, Relatory> hobbies) {
		this.hobbies = hobbies;
	}
}
