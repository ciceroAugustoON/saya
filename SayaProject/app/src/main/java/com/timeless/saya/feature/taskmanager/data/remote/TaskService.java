package com.timeless.saya.feature.taskmanager.data.remote;


import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.taskmanager.domain.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskService {

    @GET("/api/v1/user/tasks")
    Call<ApiResponse<List<Task>>> getTasks(@Header("token") String token);

    @PUT("/api/v1/user/tasks/{id}")
    Call<ApiResponse<List<Task>>> finishTask(@Header("token") String token, @Path("id") Long taskId, @Query("time") int time);
}