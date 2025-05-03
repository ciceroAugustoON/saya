package com.example.sayaproject.service;

import com.example.sayaproject.model.Task;
import com.example.sayaproject.model.Token;
import com.example.sayaproject.model.User;
import com.example.sayaproject.model.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/access/login")
    public Call<Token> login(@Body UserLogin user);
}