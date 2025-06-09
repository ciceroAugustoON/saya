package com.timeless.saya.feature.user_profile.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.timeless.saya.R;
import com.timeless.saya.feature.auth.data.repository.LoginRepository;

public class UserProfileFragment extends Fragment {
    private LoginRepository loginRepository;
    private Binding binding;

    public UserProfileFragment() {
        loginRepository = LoginRepository.getInstance(null, null);
    }

    public static Fragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        binding = new Binding(view.findViewById(R.id.username_text_view), view.findViewById(R.id.back_home_button), view.findViewById(R.id.user_profile_img));
        binding.backHomeButton.setOnClickListener(this::backHome);
        loadProfile();
        return view;
    }

    private void loadProfile() {
        // Use loginRepository para por a imagem e o nome do usuário no binding
    }

    private void backHome(View v) {
        // faça o evento para matar essa fragment e voltar para a MainView
    }

    private static class Binding {
        TextView username;
        ImageButton backHomeButton;
        ImageView profileImageView;

        public Binding(TextView username, ImageButton backHomeButton, ImageView profileImageView) {
            this.username = username;
            this.backHomeButton = backHomeButton;
            this.profileImageView = profileImageView;
        }
    }
}