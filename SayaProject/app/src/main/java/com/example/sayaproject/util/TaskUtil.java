package com.example.sayaproject.util;

import com.example.sayaproject.R;
import com.example.sayaproject.model.Habit;

public class TaskUtil {

    public static int habitToIcon(Habit habit) {
        String habitName = habit.getName();
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
