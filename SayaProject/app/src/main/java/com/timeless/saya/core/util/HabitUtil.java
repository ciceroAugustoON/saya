package com.timeless.saya.core.util;

import com.timeless.saya.R;
import com.timeless.saya.feature.taskmanager.domain.model.Task;

public class HabitUtil {

    public static int habitToIcon(String habitName) {
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
