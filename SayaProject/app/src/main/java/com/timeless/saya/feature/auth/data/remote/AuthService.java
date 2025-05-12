package com.timeless.saya.feature.auth.data.remote;

import com.timeless.saya.feature.auth.data.model.UserLogin;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/access/login")
    public Call<LoggedInUser> login(@Body UserLogin user);
}