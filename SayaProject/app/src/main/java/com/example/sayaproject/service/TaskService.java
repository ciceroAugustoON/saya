package com.example.sayaproject.service;


import com.example.sayaproject.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {

    //mockAPI example
    @GET("/api/test/tasks")
    Call<List<Task>> listTasks();


}