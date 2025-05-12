package com.timeless.saya.core;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.timeless.saya.R;
import com.timeless.saya.core.util.OnFragmentRemovedListener;
import com.timeless.saya.di.AppModule;
import com.timeless.saya.feature.auth.data.local.LocalLoginDataSource;
import com.timeless.saya.feature.auth.data.remote.RemoteLoginDataSource;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;
import com.timeless.saya.feature.auth.presentation.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnFragmentRemovedListener {

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
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

        if (!LoginRepository.getInstance(new RemoteLoginDataSource(), new LocalLoginDataSource()).isLoggedIn()) {
            bottomNavigationView.setVisibility(View.INVISIBLE);
            loadLoginFragment();
        }

        // Verificar se é a primeira vez que a Activity está sendo criada
        if (savedInstanceState == null) {
            loadTaskFragment();
        }

    }

    private void loadLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.login_container, new LoginFragment())
                .commit();
    }

    private void loadTaskFragment() {

    }
    @Override
    public void onFragmentRemoved() {
        findViewById(R.id.login_container).setVisibility(View.INVISIBLE);
        loadTaskFragment();
    }
}

