package com.timeless.saya.feature.objectives_manager.data.remote;

import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.objectives_manager.data.model.Objectives;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ObjectivesService {

    @POST("access/register/objectives")
    public Call<ApiResponse<LoggedInUser>> registerObjectives(@Header("token") String token, @Body Objectives objectives);
}
