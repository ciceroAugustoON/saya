package com.timeless.saya.feature.relatory.data.remote;

import com.timeless.saya.feature.relatory.data.model.Relatory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RelatoryService {

    @GET("/api/v1/user/relatory")
    public Call<Relatory> getRelatory(@Header("token") String token);
}
