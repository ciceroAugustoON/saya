package com.backend.saya.entities;

import java.io.Serializable;
import java.util.Objects;

import com.backend.saya.entities.enumeration.Segmentation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_habits")
public class Habit implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Segmentation segmentation;
	
	public Habit() {
	}

	public Habit(Long id, String name, Segmentation segmentation) {
		this.id = id;
		this.name = name;
		this.segmentation = segmentation;
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

	public Segmentation getSegmentation() {
		return segmentation;
	}

	public void setSegmentation(Segmentation segmentation) {
		this.segmentation = segmentation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habit other = (Habit) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Hobbies [id=" + id + ", name=" + name + "]";
	}
	
}
