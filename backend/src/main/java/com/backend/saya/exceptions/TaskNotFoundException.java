package com.backend.saya.exceptions;

public class TaskNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public TaskNotFoundException() {
		super("Task not found!");
	}
}
