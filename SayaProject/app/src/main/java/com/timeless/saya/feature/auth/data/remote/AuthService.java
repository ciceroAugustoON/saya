package com.timeless.saya.feature.auth.data.remote;

import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.auth.data.model.UserLogin;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.data.model.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/v1/access/login")
    public Call<ApiResponse<LoggedInUser>> login(@Body UserLogin user);

    @POST("/api/v1/access/register")
    public Call<ApiResponse<LoggedInUser>> register(@Body UserRegister user);
}