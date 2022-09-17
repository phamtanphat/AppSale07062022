package com.example.appsale07062022.presentation.view.acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.example.appsale07062022.R;
import com.example.appsale07062022.common.AppConstant;
import com.example.appsale07062022.data.local.AppCache;
import com.example.appsale07062022.databinding.ActivitySignInBinding;
import com.example.appsale07062022.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.splashView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                String token = (String) AppCache.getInstance(SplashActivity.this).getValue(AppConstant.TOKEN_KEY);
                Intent intent = null;
                if (token == null || token.isEmpty()) {
                    intent = new Intent(SplashActivity.this, SignInActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
}
