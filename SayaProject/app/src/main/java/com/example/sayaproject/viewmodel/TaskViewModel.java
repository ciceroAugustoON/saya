package com.example.sayaproject.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sayaproject.model.Task;
import com.example.sayaproject.model.Token;
import com.example.sayaproject.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private List<Task> tasks;

    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public List<Task> getTasks(Token token) {
        if (tasks == null) {
            tasks = repository.getTasks(token);
        }
        return tasks;
    }
}
