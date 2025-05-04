package com.example.sayaproject.database;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sayaproject.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TaskDao {
    private SharedPreferences prefs;
    private Gson gson;

    public TaskDao(SharedPreferences prefs) {
        gson = new Gson();
        this.prefs = prefs;
    }


    public void insertAll(List<Task> tasks) {
        Gson gson = new Gson();
        String taskListJson = gson.toJson(tasks);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("task_list", taskListJson);
        editor.apply();
    }

    public List<Task> getAll() {
        String savedJson = prefs.getString("task_list", null);
        Type type = new TypeToken<List<Task>>(){}.getType();
        return gson.fromJson(savedJson, type);
    }
}
