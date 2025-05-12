package com.timeless.saya.feature.auth.data.repository;

import android.util.Log;

import com.timeless.saya.feature.auth.data.local.LocalLoginDataSource;
import com.timeless.saya.feature.auth.data.remote.RemoteLoginDataSource;
import com.timeless.saya.feature.auth.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;
import com.timeless.saya.feature.auth.domain.LoginCallback;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private RemoteLoginDataSource remoteDataSource;
    private LocalLoginDataSource localDataSource;
    private LoggedInUser user = null;

    private static Result<LoggedInUser> result;

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

    public Result<LoggedInUser> login(String username, String password, LoginCallback loginCallback) {
        // handle login
        remoteDataSource.login(username, password, loginCallback);
        return result;
    }
}