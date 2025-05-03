package com.example.sayaproject.model;

public class Task {
    private Long id;
    private String name;
    private Integer duration;
    private Integer difficulty;
    private String description;


    public Task() {
    }

    public Task(Long id, String name, Integer duration, Integer difficulty, String category) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.difficulty = difficulty;
        this.description = category;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}