package com.backend.saya.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String username;
	private String password;
	@OneToOne
	private Objectives objectives;
	@OneToMany
	private List<Task> dailyTasks = new ArrayList<Task>();
	private Date taskDefinedIn;
	
	public User() {
		
	}

	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Objectives getObjectives() {
		return objectives;
	}

	public void setObjectives(Objectives objectives) {
		this.objectives = objectives;
	}

	public List<Task> getDailyTasks() {
		return dailyTasks;
	}

	public void addDailyTask(Task task) {
		dailyTasks.add(task);
	}
	
	public void removeDailyTask(Task task) {
		dailyTasks.remove(task);
	}

	public Date getTaskDefinedIn() {
		return taskDefinedIn;
	}

	public void setTaskDefinedIn(Date taskDefinedIn) {
		this.taskDefinedIn = taskDefinedIn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username + ", password=" + password + "]";
	}
}
