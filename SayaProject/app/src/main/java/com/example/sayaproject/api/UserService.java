package com.example.sayaproject.api;

import com.example.sayaproject.model.Token;
import com.example.sayaproject.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/access/login")
    public Call<Token> login(@Body UserLogin user);
}