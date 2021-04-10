package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.PanelFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.PanelFragmentViewModel;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters.ElevatorRecyclerAdapter;

import java.util.concurrent.atomic.AtomicInteger;


public class PanelFragment extends Fragment {

    private PanelFragmentLayoutBinding fragmentLayoutBinding;
    private PanelFragmentViewModel panelFragmentViewModel;
    private ElevatorRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = PanelFragmentLayoutBinding.inflate(inflater, container, false);
        panelFragmentViewModel = new ViewModelProvider(this).get(PanelFragmentViewModel.class);

        RecyclerView recyclerView = fragmentLayoutBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        adapter = new ElevatorRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        panelFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
            if(elevatorItems.size()>0) {
                fragmentLayoutBinding.tvFloors.setText(String.valueOf(elevatorItems.get(0).getMaxFloor()));
                adapter.setElevators(elevatorItems);
            } else {
                Toast.makeText(getActivity(), R.string.warn_data, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentLayoutBinding.simulationButton.setOnClickListener(v -> {
            AtomicInteger counter = new AtomicInteger();
            panelFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
                if(counter.get() == 0) {
                    counter.getAndIncrement();
                    panelFragmentViewModel.stepSimulation(elevatorItems);
                }
            });
        });

        return fragmentLayoutBinding.getRoot();
    }


}
