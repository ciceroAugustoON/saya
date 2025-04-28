package com.example.sayaproject.repository;


import com.example.sayaproject.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskRename {
    @GET("/api/test/tasks")
    Call<List<Task>> listTasks();
}