package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.PanelFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.PanelFragmentViewModel;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters.ElevatorRecyclerAdapter;

public class PanelFragment extends Fragment {

    PanelFragmentLayoutBinding fragmentLayoutBinding;

    private PanelFragmentViewModel panelFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = PanelFragmentLayoutBinding.inflate(inflater, container, false);
        panelFragmentViewModel = new ViewModelProvider(this).get(PanelFragmentViewModel.class);

        RecyclerView recyclerView = fragmentLayoutBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        ElevatorRecyclerAdapter adapter = new ElevatorRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        //panelFragmentViewModel.

        return fragmentLayoutBinding.getRoot();
    }
}
