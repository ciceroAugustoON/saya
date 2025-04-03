package com.cjmn.saya.model;

public class Task {
    private Integer id;
    private String name;
    private Integer[] duration;
    private Integer difficulty;
    private String category;

    public Task() {
    }

    public Task(Integer id, String name, Integer[] duration, Integer difficulty, String category) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.difficulty = difficulty;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getDuration() {
        return duration;
    }

    public void setDuration(Integer[] duration) {
        this.duration = duration;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
