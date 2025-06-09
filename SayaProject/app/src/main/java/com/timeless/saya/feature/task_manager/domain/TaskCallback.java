package com.timeless.saya.feature.task_manager.domain;

import com.timeless.saya.feature.task_manager.domain.model.Task;

import java.util.List;

public interface TaskCallback {
    void onLoadTasks(List<Task> tasks);

    void onTaskClicked(Task taskSelected);

    void onTaskFinished(List<Task> tasks);
}
