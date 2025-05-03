package com.example.sayaproject.service;


import com.example.sayaproject.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TaskService {

    @GET("/user/tasks")
    Call<List<Task>> listTasks(@Header("token") String token);


}