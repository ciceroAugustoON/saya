package com.cjmn.saya.repository;

import com.cjmn.saya.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {
    @GET("/api/test/tasks")
    Call<List<Task>> listTasks();
}
