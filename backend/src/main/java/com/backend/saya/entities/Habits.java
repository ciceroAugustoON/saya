package com.backend.saya.entities;

public class Habits {
    private Habit[] habitsHad;
    private Habit[] desiredHabits;

    public Habits() {
    }

    public Habits(Habit[] habitsHad, Habit[] desiredHabits) {
        this.habitsHad = habitsHad;
        this.desiredHabits = desiredHabits;
    }

    public Habit[] getHabitsHad() {
        return habitsHad;
    }

    public void setHabitsHad(Habit[] habitsHad) {
        this.habitsHad = habitsHad;
    }

    public Habit[] getDesiredHabits() {
        return desiredHabits;
    }

    public void setDesiredHabits(Habit[] desiredHabits) {
        this.desiredHabits = desiredHabits;
    }
}
