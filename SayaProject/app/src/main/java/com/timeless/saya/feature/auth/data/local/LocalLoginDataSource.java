package com.timeless.saya.feature.auth.data.local;

import com.timeless.saya.di.AppModule;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;

public class LocalLoginDataSource {
    private final UserDao userDao;

    public LocalLoginDataSource() {
        try {
            this.userDao = new UserDao(AppModule.provideSharedPreferences());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public LoggedInUser getUser() {
        return userDao.getLoggedInUser();
    }

    public void saveLogin(LoggedInUser loggedInUser) {
        userDao.saveLogin(loggedInUser);
    }

    public void removeUser() {
        userDao.removeUser();
    }
}
