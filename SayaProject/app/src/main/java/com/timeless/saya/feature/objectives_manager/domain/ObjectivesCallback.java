package com.timeless.saya.feature.objectives_manager.domain;

import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;

import java.util.List;

public interface ObjectivesCallback {

    public void onHabitsLoaded(List<Habit> habits);

    public void onPostObjectivesComplete(Result<LoggedInUser> result);
}
