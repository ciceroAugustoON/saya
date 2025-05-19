package com.timeless.saya.feature.auth.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.timeless.saya.R;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.domain.LoginCallback;

import java.util.NoSuchElementException;

public class LoginViewModel extends ViewModel implements LoginCallback {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password, this);
    }

    public void register(String email, String password, String username) {
        if (!email.contains("@")) {
            loginResult.setValue(new LoginResult(R.string.regiser_email_failed));
        } else {
            loginRepository.register(email, password, username, this);
        }
    }

    @Override
    public void onLoginComplete(Result<LoggedInUser> result) {
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            return;
        }

        Result.Error error = (Result.Error) result;
        if (error.getError() == null) {
            switch (error.getErrorCode()) {
                case 40901:
                    loginFormState.setValue(new LoginFormState(null, null, R.string.already_used_email));
                    return;
                case 40902:
                    loginFormState.setValue(new LoginFormState(R.string.already_used_username, null, null));
                    return;
            }
        }
        if (error.getError() instanceof NoSuchElementException) {
            loginResult.setValue(new LoginResult(R.string.not_found));
        } else {
            loginResult.setValue(new LoginResult(error.getError().getMessage()));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password, null));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}