package com.example.sayaproject.data;

import com.example.sayaproject.data.model.LoggedInUser;
import com.example.sayaproject.localStorage.UserDao;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private UserDao userDao;

    public Result<LoggedInUser> login(String username, String password) {

        try {

            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}