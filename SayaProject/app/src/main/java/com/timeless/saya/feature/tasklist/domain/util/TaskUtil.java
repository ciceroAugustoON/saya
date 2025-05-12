package com.timeless.saya.feature.tasklist.domain.util;

import com.timeless.saya.R;
import com.timeless.saya.feature.tasklist.domain.model.Task;

public class TaskUtil {

    public static int habitToIcon(Task task) {
        String habitName = task.getHabit();
        switch (habitName.toLowerCase()) {
            case "caminhada":
                return R.drawable.person_walking;
            case "ciclismo":
                return R.drawable.person_biking;
            case "meditação":
                return R.drawable.yin_yang;
            case "leitura":
                return R.drawable.book;
            default:
                return R.drawable.brain;
        }
    }
}
