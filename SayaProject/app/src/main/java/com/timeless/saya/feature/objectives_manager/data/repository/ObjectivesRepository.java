package com.timeless.saya.feature.objectives_manager.data.repository;

import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;
import com.timeless.saya.feature.objectives_manager.data.model.Objectives;
import com.timeless.saya.feature.objectives_manager.data.remote.RemoteObjectivesDataSource;
import com.timeless.saya.feature.objectives_manager.domain.ObjectivesCallback;

import java.util.List;

public class ObjectivesRepository implements ObjectivesCallback {
    private static ObjectivesRepository instance;

    private final RemoteObjectivesDataSource remoteDataSource;
    private List<Habit> habits;

    private ObjectivesCallback holdCallback;
    public static ObjectivesRepository getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ObjectivesRepository();
        return instance;
    }

    private ObjectivesRepository() {
        this.remoteDataSource = new RemoteObjectivesDataSource(this);
    }

    public void getHabits(ObjectivesCallback callback) {
        if (habits != null) {
            callback.onHabitsLoaded(habits);
        }
        this.holdCallback = callback;
        remoteDataSource.getHabits();
    }

    @Override
    public void onHabitsLoaded(List<Habit> habits) {
        this.habits = habits;
        if (holdCallback != null) {
            holdCallback.onHabitsLoaded(habits);
        }
    }

    @Override
    public void onPostObjectivesComplete(Result<LoggedInUser> result) {
        holdCallback.onPostObjectivesComplete(result);
    }

    public void postObjectives(String token, Objectives objectives) {
        remoteDataSource.postObjectives(token, objectives, this);
    }
}
