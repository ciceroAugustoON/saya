package com.example.sayaproject.localStorage;

import android.content.SharedPreferences;

import com.example.sayaproject.model.Token;
import com.example.sayaproject.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
public class UserDao {
    private final SharedPreferences prefs;
    private final Gson gson;

    public UserDao(SharedPreferences prefs) {
        this.prefs = prefs;
        this.gson = new Gson();
    }

    public boolean isUserLoged() {
        return getToken() != null;
    }

    public void saveToken(Token token) {
        String savedToken = gson.toJson(token);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("authentication", savedToken);
        editor.apply();
    }

    public Token getToken() {
        String savedToken = prefs.getString("authentication", null);
        Type type = new TypeToken<Token>(){}.getType();
        return gson.fromJson(savedToken, type);
    }
}
