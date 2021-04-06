package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.ActivityElevatorBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments.MainFragment;

public class ElevatorActivity extends AppCompatActivity {

    ActivityElevatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityElevatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new MainFragment());
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}