package com.timeless.saya.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.timeless.saya.R;
import com.timeless.saya.core.naviagtion.HeaderFragment;
import com.timeless.saya.core.util.OnFragmentRemovedListener;
import com.timeless.saya.di.AppModule;
import com.timeless.saya.feature.auth.data.local.LocalLoginDataSource;
import com.timeless.saya.feature.auth.data.remote.RemoteLoginDataSource;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.auth.presentation.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.timeless.saya.feature.objectives_manager.presentation.ObjectivesManagerActivity;
import com.timeless.saya.feature.relatory.presentation.RelatoryFragment;
import com.timeless.saya.feature.task_manager.presentation.TaskListFragment;
import com.timeless.saya.feature.user_profile.presentation.UserProfileFragment;


public class MainActivity extends AppCompatActivity implements OnFragmentRemovedListener {

    private static final int OBJECTIVES_MANAGER_REQUEST_CODE = 5999;

    private BottomNavigationView bottomNavigationView;
    private FragmentContainerView headerFragmentView;

    LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEdgeToEdge();
        setContentView(R.layout.activity_main);

        AppModule.setSharedPreferences(getSharedPreferences("AppPrefs", MODE_PRIVATE));

        loginRepository = LoginRepository.getInstance(new RemoteLoginDataSource(), new LocalLoginDataSource());

        initializeViews();
        setupWindowInsets();
        setupNavigation();
        checkAuthentication(savedInstanceState);
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
    }

    private void initializeViews() {
        loadFragment(R.id.header, HeaderFragment.newInstance());
        headerFragmentView = findViewById(R.id.header);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        /*ImageView profileIcon = headerFragmentView.findViewById(R.id.profile_icon);
        profileIcon.setOnClickListener(v -> loadFragment(R.id.fullscreen_container, UserProfileFragment.newInstance()));*/
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Remove o padding padrão do BottomNavigationView
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
    }

    private void setupNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                loadFragment(R.id.contentTab, TaskListFragment.newInstance(loginRepository, getApplication()));
                return true;
            }
            if (item.getItemId() == R.id.statistic) {
                loadFragment(R.id.contentTab, RelatoryFragment.newInstance());
                return true;
            }
            return false;
        });
    }

    private void checkAuthentication(Bundle savedInstanceState) {
        AppModule.setSharedPreferences(getSharedPreferences("AppPrefs", MODE_PRIVATE));

        if (!loginRepository.isLoggedIn()) {
            showAuthUI();
            loadFragment(R.id.fullscreen_container, LoginFragment.newInstance());
        } else if (savedInstanceState == null) {
            handleFirstLaunch();
        }
    }

    private void showAuthUI() {
        headerFragmentView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        findViewById(R.id.fullscreen_container).setVisibility(View.VISIBLE);
    }

    private void showMainUI() {
        headerFragmentView.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        findViewById(R.id.fullscreen_container).setVisibility(View.GONE);
    }

    private void handleFirstLaunch() {
        if (!loginRepository.isObjectivesDefined()) {
            loadObjectivesManagerActivity();
        } else {
            loadFragment(R.id.contentTab, TaskListFragment.newInstance(loginRepository, getApplication()));
        }
    }

    private Fragment loadFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
        return fragment;
    }

    @Override
    public void onFragmentRemoved() {
        if (!loginRepository.isObjectivesDefined()) {
            loadObjectivesManagerActivity();
        }
        showMainUI();
    }

    private void loadObjectivesManagerActivity() {
        Intent intent = new Intent(this, ObjectivesManagerActivity.class);
        startActivityForResult(intent, OBJECTIVES_MANAGER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OBJECTIVES_MANAGER_REQUEST_CODE && resultCode == RESULT_OK) {
            handleObjectivesManagerResult(data);
        }
    }

    private void handleObjectivesManagerResult(Intent data) {
        boolean isSuccessful = data.getBooleanExtra("successful", false);
        if (isSuccessful) {
            loginRepository.setObjectivesDefined(true);
            loadFragment(R.id.contentTab, TaskListFragment.newInstance(loginRepository, getApplication()));
        }
    }
}