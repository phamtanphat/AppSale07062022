package com.example.appsale07062022.presentation.view.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsale07062022.R;
import com.example.appsale07062022.data.model.AppResource;
import com.example.appsale07062022.data.model.User;
import com.example.appsale07062022.databinding.ActivitySignUpBinding;
import com.example.appsale07062022.presentation.viewmodel.SignUpViewModel;
import com.example.appsale07062022.util.StringUtil;

public class SignUpActivity extends AppCompatActivity {

    SignUpViewModel signUpViewModel;
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signUpViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignUpViewModel(SignUpActivity.this);
            }
        }).get(SignUpViewModel.class);

        observerData();
        event();
    }

    private void event() {
        binding.signUp.setOnClickListener(view -> {
            String email = binding.textEditEmail.getText().toString();
            String password = binding.textEditPassword.getText().toString();
            String name = binding.textEditName.getText().toString();
            String phone = binding.textEditPhone.getText().toString();
            String address = binding.textEditLocation.getText().toString();

            if (StringUtil.isValidEmail(email) && !password.isEmpty() && !name.isEmpty() && !address.isEmpty() && phone.matches("[+-]?\\d*(\\.\\d+)?")) {
                signUpViewModel.signUp(email, password, name, phone, address);
            }
        });
    }

    private void observerData() {
        signUpViewModel.getResourceUser().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status) {
                    case SUCCESS:
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        setLoading(false);
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
                        break;
                    case LOADING:
                        setLoading(true);
                        break;
                    case ERROR:
                        Toast.makeText(SignUpActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
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
