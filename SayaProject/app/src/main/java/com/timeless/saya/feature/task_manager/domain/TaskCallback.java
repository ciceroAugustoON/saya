package com.timeless.saya.feature.task_manager.domain;

import com.timeless.saya.feature.task_manager.domain.model.Task;

import java.util.List;

public interface TaskCallback {

    public void onLoadTasks(List<Task> tasks);
}
