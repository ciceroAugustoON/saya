package com.timeless.saya.feature.objectives_manager.data.remote;

import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.objectives_manager.data.model.Habit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HabitService {

    @GET("/habits")
    public Call<ApiResponse<List<Habit>>> findAllHabits();
}
