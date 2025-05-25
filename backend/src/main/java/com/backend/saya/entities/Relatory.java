package com.backend.saya.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_relatory")
@Data
@NoArgsConstructor
public class Relatory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// allDailyTasksDone to be implemented
	private Integer points = 0;
	private Integer offensive = 0;
	private Integer tasksQuantity;
	private Integer timeSave = 0;
	private Integer totalTimeSave = 0;
	@ElementCollection
	@CollectionTable(name = "taskshabit", joinColumns = @JoinColumn(name = "entidade_id"))
	@MapKeyColumn(name = "habit")
	@Column(name = "tasks")
	private Map<Long, Integer> tasksFinishedByHabit;

	public Relatory(Long id, Integer points, Integer offensive) {
		this.id = id;
		this.points = points;
		this.offensive = offensive;
		this.tasksFinishedByHabit = new HashMap<>();
	}

	public void addPoints(Integer points) {
		this.points += points;
	}

	public void increaseOffensive() {
		this.offensive++;
	}

	public void loseOffensive() {
		this.offensive = 0;
	}

	public void addTimeSaved(Integer time) {
		this.timeSave += time;
		addTotalTimeSave(time);
	}

	public void addTaskFinishedByHabit(Long habitId) {
		Integer value = tasksFinishedByHabit.get(habitId) + 1;
		tasksFinishedByHabit.put(habitId, value);
	}

	private void addTotalTimeSave(Integer timeSave) {
		this.totalTimeSave += totalTimeSave;
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
