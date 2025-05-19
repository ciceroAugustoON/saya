package com.timeless.saya.feature.objectives_manager.domain;

import com.timeless.saya.feature.objectives_manager.data.model.Habit;
import com.timeless.saya.feature.objectives_manager.data.model.Objectives;
import com.timeless.saya.feature.objectives_manager.data.repository.ObjectivesRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ObjectivesHolder {
    private int dailySpendedHours;
    private int metaReduction;
    private List<Habit> habitsHad;
    private List<Habit> desairedHabits;
    private ObjectivesRepository objectivesRepository;

    public ObjectivesHolder() {
        habitsHad = new ArrayList<>();
        desairedHabits = new ArrayList<>();
        objectivesRepository = ObjectivesRepository.getInstance();
    }

    public void setDailySpendedHours(int dailySpendedHours) {
        this.dailySpendedHours = dailySpendedHours;
    }

    public void setMetaReduction(int metaReductionPercentage) {
        if (metaReductionPercentage > 100) throw new InvalidParameterException("The percentage has to be between 0 and 100");
        this.metaReduction = dailySpendedHours * metaReductionPercentage / 100;
    }

    public void defineHabitsHad(List<Habit> habits) {
        this.habitsHad.addAll(habits);
    }

    public void defineDesiredHabits(List<Habit> habits) {
        this.desairedHabits.addAll(habits);
    }

    public void postObjectives(String token) {
        Objectives objectives = new Objectives(dailySpendedHours, metaReduction, getHabitsIds(habitsHad), getHabitsIds(desairedHabits));
        objectivesRepository.postObjectives(token, objectives);
    }

    private Long[] getHabitsIds(List<Habit> habits) {
        Long[] habitsIds = new Long[habits.size()];
        for (int i = 0; i < habitsIds.length; i++) {
            habitsIds[i] = habits.get(i).getId();
        }
        return habitsIds;
    }
}
