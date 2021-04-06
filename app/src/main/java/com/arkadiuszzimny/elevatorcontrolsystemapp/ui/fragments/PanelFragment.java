package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.PanelFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.PanelFragmentViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PanelFragment extends Fragment {

    PanelFragmentLayoutBinding fragmentLayoutBinding;
    private PanelFragmentViewModel panelFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = PanelFragmentLayoutBinding.inflate(inflater, container, false);
        panelFragmentViewModel = new ViewModelProvider(this).get(PanelFragmentViewModel.class);

        return fragmentLayoutBinding.getRoot();
    }
}
