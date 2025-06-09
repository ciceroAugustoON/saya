package com.timeless.saya.feature.taskmanager.data.repository;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.timeless.saya.core.api.ApiClient;
import com.timeless.saya.core.api.ApiResponse;
import com.timeless.saya.feature.taskmanager.data.local.TaskDao;
import com.timeless.saya.feature.taskmanager.domain.TaskCallback;
import com.timeless.saya.feature.taskmanager.domain.model.Task;
import com.timeless.saya.feature.taskmanager.data.remote.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class TaskRepository {
    private static TaskRepository instance;

    private TaskDao taskDao;
    private TaskService apiService;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private TaskRepository(Application application) {
        taskDao = new TaskDao(application.getSharedPreferences("AppPrefs", MODE_PRIVATE));
        apiService = ApiClient.getService(TaskService.class);
    }

    public static TaskRepository getInstance(Application application) {
        if (instance == null) {
            instance = new TaskRepository(application);
        }
        return instance;
    }

    public void getTasks(String token, TaskCallback taskCallback) {
        if (taskDao.getAll() != null && !taskDao.getAll().isEmpty() && taskDao.isFromToday()) {
            taskCallback.onLoadTasks(taskDao.getAll());
        } else {
            executor.execute(() -> {
                try {
                    Call<ApiResponse<List<Task>>> call = apiService.getTasks(token);
                    Response<ApiResponse<List<Task>>> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        taskDao.insertAll(response.body().getData());
                        mainHandler.post(() -> taskCallback.onLoadTasks(taskDao.getAll()));
                    } else {
                        mainHandler.post(() -> taskCallback.onLoadTasks(null));
                    }
                } catch (Exception e) {
                    Log.e("TaskRepository", e.getMessage(), e);
                }
            });
        }
    }

    public void finishTask(String token, Long taskId, int time, TaskCallback taskCallback) {
        executor.execute(() -> {
            try {
                Call<ApiResponse<List<Task>>> call = apiService.finishTask(token, taskId, time);
                Response<ApiResponse<List<Task>>> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    taskDao.insertAll(response.body().getData());
                    mainHandler.post(() -> taskCallback.onTaskFinished(taskDao.getAll()));
                }
            } catch (IOException e) {
                Log.e("TaskRepository", e.getMessage(), e);
            }
        });
    }

}
