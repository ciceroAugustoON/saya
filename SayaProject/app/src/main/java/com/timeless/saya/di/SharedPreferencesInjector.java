package com.timeless.saya.di;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;

public class SharedPreferencesInjector {

    private final Context context;

    public SharedPreferencesInjector(Context context) {
        this.context = context;
    }

    public void inject(Object target) {
        Class<?> targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Annotations.Shared.class)) {
                Annotations.Shared annotation = field.getAnnotation(Annotations.Shared.class);
                String preferencesName = annotation.value();

                // Se não foi especificado um nome, usa o padrão
                if (preferencesName.isEmpty()) {
                    preferencesName = getDefaultPreferencesName(targetClass);
                }

                SharedPreferences sharedPreferences = context.getSharedPreferences(
                        preferencesName,
                        Context.MODE_PRIVATE
                );

                try {
                    field.setAccessible(true);
                    field.set(target, sharedPreferences);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject SharedPreferences", e);
                }
            }
        }
    }

    private String getDefaultPreferencesName(Class<?> clazz) {
        return clazz.getSimpleName() + "_Prefs";
    }
}
