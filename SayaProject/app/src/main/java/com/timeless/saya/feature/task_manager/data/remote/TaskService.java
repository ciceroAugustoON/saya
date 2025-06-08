package com.timeless.saya.feature.task_manager.data.remote;


import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.task_manager.domain.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TaskService {

    @GET("/api/v1/user/tasks")
    Call<ApiResponse<List<Task>>> getTasks(@Header("token") String token);


}