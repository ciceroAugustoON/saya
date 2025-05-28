package com.timeless.saya.feature.objectives_manager.data.remote;

import android.os.Handler;
import android.os.Looper;

import com.timeless.saya.core.api.ApiClient;
import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;
import com.timeless.saya.feature.objectives_manager.data.model.Objectives;
import com.timeless.saya.feature.objectives_manager.domain.ObjectivesCallback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class RemoteObjectivesDataSource {
    private final HabitService habitService;
    private final ObjectivesService objectivesService;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ObjectivesCallback objectivesCallback;

    public RemoteObjectivesDataSource(ObjectivesCallback callback) {
        this.habitService = ApiClient.getService(HabitService.class);
        this.objectivesService = ApiClient.getService(ObjectivesService.class);
        this.objectivesCallback = callback;
    }


    public void getHabits() {
        executor.execute(() -> {
            try {
                Response<ApiResponse<List<Habit>>> response = habitService.findAllHabits().execute();
                if (response.isSuccessful()) {
                    List<Habit> habits = response.body().getData();
                    mainHandler.post(() -> objectivesCallback.onHabitsLoaded(habits));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void postObjectives(String token, Objectives objectives, ObjectivesCallback callback) {
        executor.execute(() -> {
            try {
                Response<ApiResponse<LoggedInUser>> response = objectivesService.registerObjectives(token, objectives).execute();
                if (response.isSuccessful()) {
                    mainHandler.post(() -> callback.onPostObjectivesComplete(new Result.Success<LoggedInUser>(response.body().getData())));
                } else {
                    mainHandler.post(() -> callback.onPostObjectivesComplete(new Result.Error(new Exception(response.message()))));
                }
            } catch (IOException e) {
                mainHandler.post(() -> callback.onPostObjectivesComplete(new Result.Error(e)));
            }
        });
    }
}
