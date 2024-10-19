package com.backend.saya.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.saya.entities.Task;
import com.backend.saya.exceptions.TaskNotFoundException;
import com.backend.saya.repositories.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	
	public TaskController() {
		
	}
	
	@GetMapping("")
	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	@GetMapping("/{id}")
	public Task findById(@PathVariable Long id) {
		return taskRepository.findById(id).get();
	}
	/*@PostMapping("")
	public void create(@RequestBody Task task) {
		taskRepository.save(task);
	}
	@PostMapping("/")
	public void insertAll(@RequestBody List<Task> tasks) {
		taskRepository.saveAll(tasks);
	}
	@PutMapping("/{id}")
	public void update(@RequestBody Task task, @PathVariable Long id) {
		if (task.getId() == null) {
			task.setId(id);
		}
		taskRepository.save(task);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Task> task = taskRepository.findById(id);
		if (task.isEmpty()) {
			throw new TaskNotFoundException();
		}
		taskRepository.delete(task.get());
	} */
}
