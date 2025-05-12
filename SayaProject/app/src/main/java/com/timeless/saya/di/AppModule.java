package com.timeless.saya.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.timeless.saya.feature.auth.data.model.LoggedInUser;

public class AppModule {
    private static SharedPreferences sharedPreferences;

    public static SharedPreferences provideSharedPreferences() throws IllegalAccessException {
        if (sharedPreferences == null) {
            throw new IllegalAccessException("Shared Preferences is not defined!");
        }
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences prefs) {
        sharedPreferences = prefs;
    }
}
