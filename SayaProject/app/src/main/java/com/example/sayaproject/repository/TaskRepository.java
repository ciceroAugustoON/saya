package com.example.sayaproject.repository;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.util.Log;

import com.example.sayaproject.api.ApiClient;
import com.example.sayaproject.database.TaskDao;
import com.example.sayaproject.model.Task;
import com.example.sayaproject.model.Token;
import com.example.sayaproject.api.TaskService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TaskRepository {
    private TaskDao taskDao;
    private TaskService apiService;

    public TaskRepository(Application application) {
        taskDao = new TaskDao(application.getSharedPreferences("AppPrefs", MODE_PRIVATE));
        apiService = ApiClient.getTaskService();
    }

    public List<Task> getTasks(Token token) {
        refreshTasks(token);
        List<Task> localTasks = taskDao.getAll();

        return localTasks;
    }

    private void refreshTasks(Token token) {
        new Thread(() -> {
            try {
                Call<List<Task>> call = apiService.getTasks(token.getToken());
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
