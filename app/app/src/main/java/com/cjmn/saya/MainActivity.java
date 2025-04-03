package com.cjmn.saya;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cjmn.saya.model.Task;
import com.cjmn.saya.repository.TaskService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Removing bottom padding added by the sdk
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        //
        bottomNavigationView.findViewById(R.id.home).setOnClickListener((l) -> {
            TaskService taskService = getTaskService();
            Call<List<Task>> callTasks = taskService.listTasks();

            callTasks.enqueue(new Callback<List<Task>>() {
                @Override
                public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                    List<Task> tasks = response.body();
                    ListView listView = findViewById(R.id.tasks);
                    String[] tasksString = tasksToArray(tasks);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tasksString);
                    listView.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<List<Task>> call, Throwable throwable) {
                    System.out.println(throwable);
                }
            });
        });

    }

    protected TaskService getTaskService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://67eae66234bcedd95f64ef06.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TaskService.class);
    }

    protected String[] tasksToArray(List<Task> tasks) {
        if (tasks == null) {
            return new String[0];
        }

        String[] result = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            result[i] = task != null ? task.getName() : null;
        }
        return result;
    }
}