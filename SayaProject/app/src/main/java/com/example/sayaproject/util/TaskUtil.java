package com.example.sayaproject.util;

import android.media.Image;

import com.example.sayaproject.R;
import com.example.sayaproject.model.Task;

public class TaskUtil {

    public static int habitToIcon(Task task) {
        String habitName = task.getHabit().getName();
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
