package com.timeless.saya.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;

import com.timeless.saya.R;
import com.timeless.saya.core.util.OnFragmentRemovedListener;
import com.timeless.saya.di.AppModule;
import com.timeless.saya.feature.auth.data.local.LocalLoginDataSource;
import com.timeless.saya.feature.auth.data.remote.RemoteLoginDataSource;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.auth.presentation.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.timeless.saya.feature.objectives_manager.presentation.ObjectivesManagerActivity;
import com.timeless.saya.feature.taskmanager.presentation.TaskListFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentRemovedListener {
    BottomNavigationView bottomNavigationView;
    FragmentContainerView headerFragmentView;

    LoginRepository loginRepository;

    int REQUEST_CODE = 5999;

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

        AppModule.setSharedPreferences(getSharedPreferences("AppPrefs", MODE_PRIVATE));

        headerFragmentView = findViewById(R.id.header);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Removendo o padding de baixo adicionado pelo sdk
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        //
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                loadTaskFragment();
                return true;
            }
            return false;
        });

        loginRepository = LoginRepository.getInstance(new RemoteLoginDataSource(), new LocalLoginDataSource());

        if (!loginRepository.isLoggedIn()) {
            headerFragmentView.setVisibility(View.INVISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
            loadLoginFragment();
        } else if (savedInstanceState == null) {
            if (!loginRepository.isObjectivesDefined()) {loadHabitManagerActivity();}
            else {loadTaskFragment();}
        }

    }

    private void loadLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.login_container, new LoginFragment())
                .commit();
    }

    private void loadTaskFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.taskListContainer, new TaskListFragment(loginRepository, this.getApplication()))
                .commit();
    }
    @Override
    public void onFragmentRemoved() {
        if (!loginRepository.isObjectivesDefined()) {
            loadHabitManagerActivity();
        }
        headerFragmentView.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void loadHabitManagerActivity() {
        Intent intent = new Intent(this, ObjectivesManagerActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            boolean returnedValue = data.getBooleanExtra("successful", false);
            loginRepository.toogleObjectivesDefined();
            if (returnedValue) loadTaskFragment();
        }
    }
}

