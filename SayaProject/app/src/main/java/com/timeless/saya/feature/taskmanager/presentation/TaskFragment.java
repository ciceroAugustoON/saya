package com.timeless.saya.feature.taskmanager.presentation;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.timeless.saya.R;
import com.timeless.saya.core.MainActivity;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.taskmanager.data.repository.TaskRepository;
import com.timeless.saya.feature.taskmanager.domain.TaskCallback;
import com.timeless.saya.feature.taskmanager.domain.model.Task;

import java.util.List;
import java.util.Locale;

public class TaskFragment extends Fragment {
    private LoginRepository loginRepository;
    private TaskRepository taskRepository;
    private Binding binding;
    private boolean isExecuting;
    private int seconds;
    private Task task;
    private TaskCallback taskCallback;

    public static TaskFragment getInstance(Task task, Application application, TaskCallback taskCallback) {
        return new TaskFragment(task, application, taskCallback);
    }

    public TaskFragment(Task task, Application application, TaskCallback taskCallback) {
        this.task = task;
        this.taskRepository = TaskRepository.getInstance(application);
        this.loginRepository = LoginRepository.getInstance(null, null);
        this.taskCallback = taskCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        binding = new Binding(view.findViewById(R.id.task_name_textview), view.findViewById(R.id.chronometer_textview), view.findViewById(R.id.init_task_imageButton), view.findViewById(R.id.toggle_task_imageButton), view.findViewById(R.id.finish_task_imageButton));
        binding.taskNameTextView.setText(this.task.getName());

        initTimer();

        binding.playImageButton.setOnClickListener((v) -> {
            isExecuting = true;
            binding.playImageButton.setVisibility(View.GONE);
            binding.toggleImageButton.setVisibility(View.VISIBLE);
            binding.toggleImageButton.setOnClickListener((w) -> {
                isExecuting = !isExecuting;
                binding.toggleImageButton.setImageResource((isExecuting) ? R.drawable.pause : R.drawable.play_solid);
            });
            binding.finishImageButton.setVisibility(View.VISIBLE);
            binding.finishImageButton.setOnClickListener((x) -> {
                isExecuting = false;
                taskRepository.finishTask(loginRepository.getToken(), task.getId(), seconds / 60, new TaskCallback() {
                    @Override
                    public void onLoadTasks(List<Task> tasks) {

                    }

                    @Override
                    public void onTaskClicked(Task taskSelected) {

                    }

                    @Override
                    public void onTaskFinished(List<Task> tasks) {
                        close();
                    }
                });
            });
        });

        return view;
    }

    private void initTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int horas = seconds / 3600;
                int minutos = (seconds % 3600) / 60;
                int segs = seconds % 60;

                String tempo = String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segs);
                binding.chronometerTextView.setText(tempo);

                if (isExecuting) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    private void close() {
        MainActivity mainActivity = (MainActivity) this.getHost();
        getParentFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
        if (mainActivity != null) {
            mainActivity.onFragmentRemoved();
        }
    }

    private static class Binding {
        TextView taskNameTextView;
        TextView chronometerTextView;
        ImageButton playImageButton;
        ImageButton toggleImageButton;
        ImageButton finishImageButton;

        public Binding(TextView taskNameTextView, TextView chronometerTextView, ImageButton playImageButton, ImageButton toggleImageButton, ImageButton finishImageButton) {
            this.taskNameTextView = taskNameTextView;
            this.chronometerTextView = chronometerTextView;
            this.playImageButton = playImageButton;
            this.toggleImageButton = toggleImageButton;
            this.finishImageButton = finishImageButton;
        }
    }
}
