package com.timeless.saya.feature.taskmanager.presentation;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.timeless.saya.R;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.taskmanager.data.repository.TaskRepository;
import com.timeless.saya.feature.taskmanager.domain.model.Task;

import java.util.List;

public class TaskListFragment extends Fragment {

    private ProgressBar loading;
    private ListView listView;

    private final TaskRepository taskRepository;
    private final LoginRepository loginRepository;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        loading = view.findViewById(R.id.loading2);
        listView = view.findViewById(R.id.taskList);
        String token = loginRepository.getToken();
        taskRepository.getTasks(token, this);
        return view;
    }

    public void onLoadTasks(List<Task> tasks) {
        loading.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        ListView listView = view.findViewById(R.id.taskList);
        listView.setAdapter(new TaskViewAdapter(listView.getContext(), tasks));
    }
}