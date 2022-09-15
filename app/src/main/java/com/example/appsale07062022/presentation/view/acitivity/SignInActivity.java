package com.example.appsale07062022.presentation.view.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appsale07062022.R;
import com.example.appsale07062022.data.model.AppResource;
import com.example.appsale07062022.data.model.User;
import com.example.appsale07062022.databinding.ActivitySignInBinding;
import com.example.appsale07062022.presentation.viewmodel.SignInViewModel;

public class SignInActivity extends AppCompatActivity {

    SignInViewModel signInViewModel;
    ActivitySignInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signInViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel(SignInActivity.this);
            }
        }).get(SignInViewModel.class);

        observerData();
        event();
    }

    private void event() {
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.textEditEmail.getText().toString();
                String password = binding.textEditPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                signInViewModel.signIn(email, password);
            }
        });
    }

    private void observerData() {
        signInViewModel.getResourceUser().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status) {
                    case SUCCESS:
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        setLoading(false);
                        break;
                    case LOADING:
                        setLoading(true);
                        break;
                    case ERROR:
                        Toast.makeText(SignInActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
                        setLoading(false);
                        break;
                }
            }
        });
    }

    private void setLoading(Boolean isLoading) {
        if (isLoading) {
            binding.layoutLoading.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutLoading.getRoot().setVisibility(View.GONE);
        }
    }
}
