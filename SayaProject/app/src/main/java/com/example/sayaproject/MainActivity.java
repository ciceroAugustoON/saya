package com.example.sayaproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.sayaproject.layout.TaskAdapter;
import com.example.sayaproject.model.Task;
import com.example.sayaproject.model.Token;
import com.example.sayaproject.model.UserLogin;
import com.example.sayaproject.api.UserService;
import com.example.sayaproject.viewmodel.TaskViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final static String baseURL = "http://192.168.0.31:8080/";
    private static Token token;
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

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
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        UserService userService = getUserService();
        Call<Token> callToken = userService.login(new UserLogin("joao", "1234"));
        callToken.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    // Atualize a UI aqui com os dados recebidos
                    token = response.body();
                } else {
                    Log.e("API", "Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("API", "Falha na requisição", t);
            }
        });

        loadTasks();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Removing bottom padding added by the sdk
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        //
        bottomNavigationView.findViewById(R.id.home).setOnClickListener((l) -> {
            List<Task> tasks = taskViewModel.getTasks(token);
            adapter = new TaskAdapter(MainActivity.this, tasks);
            ListView taskListView = findViewById(R.id.tasks);
            taskListView.setAdapter(adapter);
        });

    }

    private void loadTasks() {
        List<Task> tasks = taskViewModel.getTasks(token);
        adapter = new TaskAdapter(MainActivity.this, tasks);
        ListView taskListView = findViewById(R.id.tasks);
        taskListView.setAdapter(adapter);
    }

    protected UserService getUserService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UserService.class);
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

