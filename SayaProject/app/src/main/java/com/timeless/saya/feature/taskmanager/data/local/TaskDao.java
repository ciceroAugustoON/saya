package com.timeless.saya.feature.taskmanager.data.local;

import android.content.SharedPreferences;
import android.os.Build;

import com.timeless.saya.feature.taskmanager.domain.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDao {
    private final SharedPreferences prefs;
    private final Gson gson;

    public TaskDao(SharedPreferences prefs) {
        gson = new Gson();
        this.prefs = prefs;
    }

    public void insertAll(List<Task> tasks) {
        String taskListJson = gson.toJson(tasks);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("task_list", taskListJson);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editor.putString("task_data", LocalDate.now().toString());
        }
        editor.apply();
    }

    public List<Task> getAll() {
        String savedJson = prefs.getString("task_list", null);
        Type type = new TypeToken<List<Task>>(){}.getType();
        return gson.fromJson(savedJson, type);
    }

    public boolean isFromToday() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate time = LocalDate.parse(prefs.getString("task_data", null));
            LocalDate today = LocalDate.now();
            return !today.isAfter(time);
        }
        return false;
    }
}
