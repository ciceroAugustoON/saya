package com.example.sayaproject.model;

import java.util.List;

public class User {
    private Long id;
    private String email;
    private String userName;
    private String token;
    private List<Task> tasks;

    public User(Long id, String email, String userName, String token, List<Task> tasks) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.token = token;
        this.tasks = tasks;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
