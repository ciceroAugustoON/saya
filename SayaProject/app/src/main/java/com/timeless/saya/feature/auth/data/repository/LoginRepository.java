package com.timeless.saya.feature.auth.data.repository;

import com.timeless.saya.feature.auth.data.local.LocalLoginDataSource;
import com.timeless.saya.feature.auth.data.remote.RemoteLoginDataSource;
import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.domain.LoginCallback;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository implements LoginCallback{

    private static volatile LoginRepository instance;

    private RemoteLoginDataSource remoteDataSource;
    private LocalLoginDataSource localDataSource;
    private LoggedInUser user = null;

    private static LoginCallback fatherLoginCallback;

    // private constructor : singleton access
    private LoginRepository(RemoteLoginDataSource dataSource, LocalLoginDataSource localDataSource) {
        this.remoteDataSource = dataSource;
        this.localDataSource = localDataSource;
        this.user = localDataSource.getUser();
    }

    public static LoginRepository getInstance(RemoteLoginDataSource dataSource, LocalLoginDataSource localDataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource, localDataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isObjectivesDefined() {
        return user.isObjectivesDefined();
    };

    public boolean toogleObjectivesDefined() {
        user.setObjectivesDefined(!user.isObjectivesDefined());
        localDataSource.saveLogin(user);
        return user.isObjectivesDefined();
    }

    public void logout() {
        user = null;
        localDataSource.removeUser();
        remoteDataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        localDataSource.saveLogin(user);
    }

    public String getToken() {
        return user.getToken();
    }

    public void login(String username, String password, LoginCallback loginCallback) {
        fatherLoginCallback = loginCallback;
        remoteDataSource.login(username, password, this);
    }

    public void register(String email, String password, String username, LoginCallback loginCallback) {
        fatherLoginCallback = loginCallback;
        remoteDataSource.register(email, username, password, this);
    }

    @Override
    public void onLoginComplete(Result<LoggedInUser> result) {
        if (fatherLoginCallback == null) {
            throw new RuntimeException("login callback is not provided");
        }
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }

        fatherLoginCallback.onLoginComplete(result);
    }

    public void reloadLoggedInUser(LoggedInUser data) {
        setLoggedInUser(data);
        localDataSource.saveLogin(data);
    }

    public void setObjectivesDefined(boolean b) {
        user.setObjectivesDefined(b);
        localDataSource.saveLogin(user);
    }
}