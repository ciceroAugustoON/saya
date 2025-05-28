package com.timeless.saya.feature.taskmanager.presentation;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.timeless.saya.R;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.taskmanager.data.repository.TaskRepository;
import com.timeless.saya.feature.taskmanager.domain.model.Task;

import java.util.List;

public class TaskListFragment extends Fragment {

    private ProgressBar loading;
    private ListView listView;
    private TextView errorMessage;
    private Button tryAgainButton;

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
        errorMessage = view.findViewById(R.id.errorLoadTaskTextView);
        tryAgainButton = view.findViewById(R.id.try_again_load_tasks);

        String token = loginRepository.getToken();
        taskRepository.getTasks(token, this);
        return view;
    }

    public void onLoadTasks(List<Task> tasks) {
        loading.setVisibility(View.GONE);
        if (tasks != null && !tasks.isEmpty()) {
            listView.setVisibility(View.VISIBLE);
            ListView listView = view.findViewById(R.id.taskList);
            listView.setAdapter(new TaskViewAdapter(listView.getContext(), tasks));
        } else {
            errorMessage.setVisibility(View.VISIBLE);
            tryAgainButton.setVisibility(View.VISIBLE);
            tryAgainButton.setOnClickListener((view) -> {
                loading.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.GONE);
                tryAgainButton.setVisibility(View.GONE);
                taskRepository.getTasks(loginRepository.getToken(), this);
            });
        }

    }
}