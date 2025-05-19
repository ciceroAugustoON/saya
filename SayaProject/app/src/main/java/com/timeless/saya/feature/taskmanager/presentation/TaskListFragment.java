package com.timeless.saya.feature.taskmanager.presentation;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.timeless.saya.R;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.taskmanager.data.repository.TaskRepository;
import com.timeless.saya.feature.taskmanager.domain.model.Task;

import java.util.List;

public class TaskListFragment extends Fragment {

    private final TaskRepository taskRepository;
    private final LoginRepository loginRepository;

    public TaskListFragment(LoginRepository loginRepository, Application application) {
        this.loginRepository = loginRepository;
        taskRepository = new TaskRepository(application);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        ListView listView = view.findViewById(R.id.taskList);
        // Set the adapter
        String token = loginRepository.getToken();
        List<Task> tasks = taskRepository.getTasks(token);

        listView.setAdapter(new TaskViewAdapter(listView.getContext(), tasks));
        return view;
    }
}