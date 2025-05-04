package com.example.sayaproject.api;


import com.example.sayaproject.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TaskService {

    @GET("/user/tasks")
    Call<List<Task>> getTasks(@Header("token") String token);


}