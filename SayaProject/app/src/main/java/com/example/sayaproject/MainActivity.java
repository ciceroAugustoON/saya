package com.example.sayaproject;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.sayaproject.layout.TaskAdapter;
import com.example.sayaproject.localStorage.UserDao;
import com.example.sayaproject.model.Task;
import com.example.sayaproject.model.Token;
import com.example.sayaproject.model.UserLogin;
import com.example.sayaproject.api.UserService;
import com.example.sayaproject.viewmodel.TaskViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final static String baseURL = "http://192.168.0.31:8080/";
    private UserDao userDao;
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

        userDao = new UserDao(this.getSharedPreferences("AppPrefs", MODE_PRIVATE));

        if (!userDao.isUserLoged()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        loadTasks();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Removing bottom padding added by the sdk
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                // Atualiza a UI para a tela "Home"
                List<Task> tasks = taskViewModel.getTasks(userDao.getToken());
                adapter = new TaskAdapter(MainActivity.this, tasks);
                ListView taskListView = findViewById(R.id.tasks);
                taskListView.setAdapter(adapter);

                // Marca o item como selecionado (gerenciado automaticamente pelo BottomNavigationView)
                return true; // Retorna true para indicar que o clique foi tratado
            }

            return false; // Se não for tratado
        });

    }

    private void loadTasks() {
        List<Task> tasks = taskViewModel.getTasks(userDao.getToken());
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

