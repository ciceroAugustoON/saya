package com.timeless.saya.feature.auth.data.local;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;

import java.lang.reflect.Type;

public class UserDao {
    private final SharedPreferences prefs;
    private final Gson gson;

    public UserDao(SharedPreferences prefs) {
        this.prefs = prefs;
        this.gson = new Gson();
    }

    public boolean isUserLogged() {
        return getLoggedInUser() != null;
    }

    public void saveLogin(LoggedInUser token) {
        String savedUser = gson.toJson(token);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("authentication", savedUser);
        editor.apply();
    }

    public LoggedInUser getLoggedInUser() {
        String savedUser = prefs.getString("authentication", null);
        Type type = new TypeToken<LoggedInUser>(){}.getType();
        return gson.fromJson(savedUser, type);
    }

    public void removeUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("authentication");
        editor.apply();
    }
}
