package com.timeless.saya.feature.objectives_manager.data.model;

public class Objectives {
    private int dailySpendedHours;
    private int metaReduction;
    private Long[] habitsHad;
    private Long[] desiredHabits;

    public Objectives() {
    }

    public Objectives(int dailySpendedHours, int metaReduction, Long[] habitsHad, Long[] desiredHabits) {
        this.dailySpendedHours = dailySpendedHours;
        this.metaReduction = metaReduction;
        this.habitsHad = habitsHad;
        this.desiredHabits = desiredHabits;
    }

    public int getDailySpendedHours() {
        return dailySpendedHours;
    }

    public void setDailySpendedHours(int dailySpendedHours) {
        this.dailySpendedHours = dailySpendedHours;
    }

    public int getMetaReduction() {
        return metaReduction;
    }

    public void setMetaReduction(int metaReduction) {
        this.metaReduction = metaReduction;
    }

    public Long[] getHabitsHad() {
        return habitsHad;
    }

    public void setHabitsHad(Long[] habitsHad) {
        this.habitsHad = habitsHad;
    }

    public Long[] getDesiredHabits() {
        return desiredHabits;
    }

    public void setDesiredHabits(Long[] desiredHabits) {
        this.desiredHabits = desiredHabits;
    }
}
