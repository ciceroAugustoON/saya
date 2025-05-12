package com.timeless.saya.feature.auth.data.remote;

import android.os.Handler;
import android.os.Looper;

import com.timeless.saya.core.api.ApiClient;
import com.timeless.saya.feature.auth.data.model.UserLogin;
import com.timeless.saya.feature.auth.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.domain.LoginCallback;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class RemoteLoginDataSource {
    private final AuthService authService;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public RemoteLoginDataSource() {
        authService = ApiClient.getService(AuthService.class);
    }

    public void login(String username, String password, LoginCallback callback) {
        executor.execute(() -> {
            try {
                Response<LoggedInUser> response = authService.login(new UserLogin(username, password)).execute();
                LoggedInUser loggedInUser = response.body();

                if (loggedInUser == null) {
                    throw new IOException("Invalid response");
                }

                // Envia sucesso para a thread principal
                mainHandler.post(() -> callback.onLoginComplete(new Result.Success<>(loggedInUser)));
            } catch (Exception e) {
                // Envia erro para a thread principal
                mainHandler.post(() -> callback.onLoginComplete(new Result.Error(e)));
            }
        });
    }

    public void logout() {
        // TODO: revoke authentication
    }
}