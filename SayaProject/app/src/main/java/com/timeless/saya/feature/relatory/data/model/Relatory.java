package com.timeless.saya.feature.relatory.data.model;

import java.util.Map;

public class Relatory {
    private int offensive;
    private int timeSaved;
    private Map<Long, Integer> tasksFinishedByHabit;

    public Relatory() {
    }

    public Relatory(int offensive, int timeSaved, Map<Long, Integer> tasksFinishedByHabit) {
        this.offensive = offensive;
        this.timeSaved = timeSaved;
        this.tasksFinishedByHabit = tasksFinishedByHabit;
    }

    public int getOffensive() {
        return offensive;
    }

    public void setOffensive(int offensive) {
        this.offensive = offensive;
    }

    public int getTimeSaved() {
        return timeSaved;
    }

    public void setTimeSaved(int timeSaved) {
        this.timeSaved = timeSaved;
    }

    public Map<Long, Integer> getTasksFinishedByHabit() {
        return tasksFinishedByHabit;
    }

    public void setTasksFinishedByHabit(Map<Long, Integer> tasksFinishedByHabit) {
        this.tasksFinishedByHabit = tasksFinishedByHabit;
    }
}

