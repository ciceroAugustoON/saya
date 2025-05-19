package com.timeless.saya.feature.taskmanager.data.repository;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.util.Log;

import com.timeless.saya.core.api.ApiClient;
import com.timeless.saya.feature.taskmanager.data.local.TaskDao;
import com.timeless.saya.feature.taskmanager.domain.model.Task;
import com.timeless.saya.feature.taskmanager.data.remote.TaskService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TaskRepository {
    private TaskDao taskDao;
    private TaskService apiService;

    public TaskRepository(Application application) {
        taskDao = new TaskDao(application.getSharedPreferences("AppPrefs", MODE_PRIVATE));
        apiService = ApiClient.getService(TaskService.class);
    }

    public List<Task> getTasks(String token) {
        refreshTasks(token);

        return taskDao.getAll();
    }

    private void refreshTasks(String token) {
        new Thread(() -> {
            try {
                Call<List<Task>> call = apiService.getTasks(token);
                Response<List<Task>> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    taskDao.insertAll(response.body());
                }
            } catch (Exception e) {
                Log.e("TaskRepository", "Erro ao atualizar tasks", e);
            }
        }).start();
    }
}
