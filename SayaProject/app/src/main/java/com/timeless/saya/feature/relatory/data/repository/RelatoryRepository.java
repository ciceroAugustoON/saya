package com.timeless.saya.feature.relatory.data.repository;

import com.timeless.saya.core.api.ApiClient;
import com.timeless.saya.core.util.RemoteDataCallback;
import com.timeless.saya.feature.relatory.data.model.Relatory;
import com.timeless.saya.feature.relatory.data.remote.RelatoryService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatoryRepository {
    private static RelatoryRepository instance;

    private RelatoryService relatoryService;

    private RelatoryRepository() {
        relatoryService = ApiClient.getService(RelatoryService.class);
    }

    public static RelatoryRepository getInstance() {
        if (instance == null) {
            instance = new RelatoryRepository();
        }
        return instance;
    }

    public void getRelatory(String token, RemoteDataCallback<Relatory> remoteDataCallback) {
        relatoryService.getRelatory(token).enqueue(new Callback<Relatory>() {
            @Override
            public void onResponse(Call<Relatory> call, Response<Relatory> response) {
                if (response.isSuccessful()) remoteDataCallback.onRecoverData(response.body());
            }

            @Override
            public void onFailure(Call<Relatory> call, Throwable throwable) {

            }
        });
    }
}
