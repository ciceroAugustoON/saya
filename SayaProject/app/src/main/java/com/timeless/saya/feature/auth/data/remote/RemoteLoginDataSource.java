package com.timeless.saya.feature.auth.data.remote;

import android.accounts.AuthenticatorException;
import android.nfc.FormatException;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timeless.saya.core.api.ApiClient;
import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.auth.data.model.UserLogin;
import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.data.model.UserRegister;
import com.timeless.saya.feature.auth.domain.LoginCallback;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
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
                Response<ApiResponse<LoggedInUser>> response = authService.login(new UserLogin(username, password)).execute();
                switch(response.code()) {
                    case 404:
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(new NoSuchElementException("Conta não encontrada!"))));
                        break;
                    case 400:
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(new InvalidParameterException("Email e senha são obrigatórios!"))));
                        break;
                    case 401:
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(new AuthenticatorException("Credenciais inválidas!"))));
                        break;
                    default:
                        ApiResponse<LoggedInUser> apiResponse = response.body();
                        LoggedInUser loggedInUser = apiResponse.getData();
                        // Envia sucesso para a thread principal
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Success<>(loggedInUser)));

                }

            } catch (Exception e) {
                // Envia erro para a thread principal
                mainHandler.post(() -> callback.onLoginComplete(new Result.Error(e)));
            }
        });
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void register(String email, String username, String password, LoginCallback callback) {
        executor.execute(() -> {
            try {
                Response<ApiResponse<LoggedInUser>> response = authService.register(new UserRegister(email, username, password)).execute();
                ApiResponse<LoggedInUser> apiResponse = response.body();
                if (apiResponse == null) {
                    try (ResponseBody errorBody = response.errorBody()) {
                         apiResponse = new Gson().fromJson(
                                 errorBody.charStream(),
                                 new TypeToken<ApiResponse<LoggedInUser>>(){}.getType()
                         );
                    }
                }
                String message = (apiResponse.getMessage() != null) ? apiResponse.getMessage() : "";
                if (response.code() == 400) {
                    if ("All fields are required".equals(message)) {
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(new InvalidParameterException("Todos os campos são obrigatórios!"))));
                    } else if ("Invalid email format".equals(message)) {
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(new FormatException("Formato de email inválido!"))));
                    }
                } else if (response.code() == 409) {
                    if ("Email already registered".equals(message)) {
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(40901)));
                    } else if ("Username already registered".equals(message)) {
                        mainHandler.post(() -> callback.onLoginComplete(new Result.Error(40902)));
                    }
                } else if (response.code() == 500) {
                    mainHandler.post(() -> callback.onLoginComplete(new Result.Error(new Exception("Ocorreu um erro durante o processamento do registro. Tente novamente mais tarde."))));
                } else if (response.code() == 200) {
                    LoggedInUser loggedInUser = apiResponse.getData();
                    mainHandler.post(() -> callback.onLoginComplete(new Result.Success<>(loggedInUser)));
                }
            } catch (IOException e) {
                mainHandler.post(() -> callback.onLoginComplete(new Result.Error(e)));
            }
        });
    }
}