package com.timeless.saya.feature.taskmanager.domain.model;

import com.timeless.saya.feature.objectives_manager.data.model.enumeration.Difficulty;
public class Task {
    private Long id;
    private String name;
    private Integer timeSecs;
    private Difficulty difficulty;
    private String description;

    private String habit;

    public Task() {
    }

    public Task(Long id, String name, Integer duration, Difficulty difficulty, String description, String habit) {
        this.id = id;
        this.name = name;
        this.timeSecs = duration;
        this.difficulty = difficulty;
        this.description = description;
        this.habit = habit;
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

    public Integer getTimeSecs() {
        return timeSecs;
    }

    public void setTimeSecs(Integer timeSecs) {
        this.timeSecs = timeSecs;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = Difficulty.valueOf(difficulty);
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }
}