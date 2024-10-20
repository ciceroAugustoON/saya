package com.backend.saya.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_relatory")
public class Relatory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int percentualConclusionYesterday;
	
	public Relatory() {
	}
	
	public Relatory(Long id, int percentualConclusionYesterday) {
		this.id = id;
		this.percentualConclusionYesterday = percentualConclusionYesterday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPercentualConclusionYesterday() {
		return percentualConclusionYesterday;
	}

	public void setPercentualConclusionYesterday(int percentualConclusionYesterday) {
		this.percentualConclusionYesterday = percentualConclusionYesterday;
	}

	@Override
	public String toString() {
		return "Relatory [id=" + id + ", percentualConclusionYesterday=" + percentualConclusionYesterday + "]";
	}
}
