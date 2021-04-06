package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.MainFragmentLayoutBinding;

public class MainFragment extends Fragment {

    MainFragmentLayoutBinding fragmentLayoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = MainFragmentLayoutBinding.inflate(inflater, container, false);

        return fragmentLayoutBinding.getRoot();
    }
}
