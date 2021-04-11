package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.SplashScreenLayoutBinding;

public class SplashScreen  extends AppCompatActivity {

    private SplashScreenLayoutBinding splashScreenLayoutBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenLayoutBinding = SplashScreenLayoutBinding.inflate(getLayoutInflater());
        setContentView(splashScreenLayoutBinding.getRoot());

        Animation leftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
        Animation rightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);

        splashScreenLayoutBinding.logoLeft.startAnimation(leftAnimation);
        splashScreenLayoutBinding.logoRight.startAnimation(rightAnimation);

        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(SplashScreen.this, ElevatorActivity.class);
            startActivity(homeIntent);
            finish();
        }, 1400L);
    }
}
