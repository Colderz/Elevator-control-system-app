package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.PanelFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.PanelFragmentViewModel;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters.ElevatorRecyclerAdapter;
import com.arkadiuszzimny.elevatorcontrolsystemapp.util.BubbleInterpolator;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class PanelFragment extends Fragment {

    private PanelFragmentLayoutBinding fragmentLayoutBinding;
    private PanelFragmentViewModel panelFragmentViewModel;
    private ElevatorRecyclerAdapter adapter;
    private Animation bubble;
    private BubbleInterpolator bubbleInterpolator;
    private int clickSimulationCounter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = PanelFragmentLayoutBinding.inflate(inflater, container, false);
        panelFragmentViewModel = new ViewModelProvider(this).get(PanelFragmentViewModel.class);

        RecyclerView recyclerView = fragmentLayoutBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        clickSimulationCounter = 0;

        adapter = new ElevatorRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        bubble = AnimationUtils.loadAnimation(getActivity(), R.anim.bubble);
        bubbleInterpolator = new BubbleInterpolator(0.2, 20);
        bubble.setInterpolator(bubbleInterpolator);

        panelFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
            if (elevatorItems.size() > 0) {
                int maxFloor = elevatorItems.get(0).getMaxFloor();
                fragmentLayoutBinding.tvFloors.setText(String.valueOf(maxFloor));
                setupPickerFloor(fragmentLayoutBinding.pickerFloor, maxFloor);
                adapter.setElevators(elevatorItems);
            } else {
                Toast.makeText(getActivity(), R.string.warn_data, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentLayoutBinding.simulationButton.setOnClickListener(v -> {
            clickSimulationCounter++;
            if (!(clickSimulationCounter > 6)) {
                AtomicInteger counter = new AtomicInteger();
                panelFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
                    if (counter.get() == 0) {
                        counter.getAndIncrement();
                        panelFragmentViewModel.stepSimulation(elevatorItems);
                    }
                });
                fragmentLayoutBinding.simulationButton.startAnimation(bubble);
            } else {
                Toast.makeText(fragmentLayoutBinding.simulationButton.getContext(), R.string.info_sim_end, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentLayoutBinding.buttonUp.setOnClickListener(v -> {
            hideFloorPicker();
            showLevelWantPicker();
            setupPickerLevelWant(fragmentLayoutBinding.pickerLevelWant, Integer.valueOf(String.valueOf(fragmentLayoutBinding.tvFloors.getText())), fragmentLayoutBinding.pickerFloor.getValue());
        });

        fragmentLayoutBinding.buttonDown.setOnClickListener(v -> {
            hideFloorPicker();
            showLevelWantPicker();
            setupPickerLevelWant(fragmentLayoutBinding.pickerLevelWant, fragmentLayoutBinding.pickerFloor.getValue(), 0);
        });


        fragmentLayoutBinding.buttonChoose.setOnClickListener(v -> {
            hideLevelWantPicker();
            showFloorPicker();
        });

        return fragmentLayoutBinding.getRoot();
    }

    private void showLevelWantPicker() {
        fragmentLayoutBinding.cardLevelWant.setVisibility(View.VISIBLE);
    }

    private void showFloorPicker() {
        fragmentLayoutBinding.cardFloorPicker.setVisibility(View.VISIBLE);
    }

    private void hideFloorPicker() {
        fragmentLayoutBinding.cardFloorPicker.setVisibility(View.INVISIBLE);
    }

    private void hideLevelWantPicker() {
        fragmentLayoutBinding.cardLevelWant.setVisibility(View.INVISIBLE);
    }

    private void setupPickerFloor(NumberPicker picker, int floors) {
        picker.setMinValue(0);
        picker.setMaxValue(floors);
        picker.setValue(1);
        picker.setOnValueChangedListener((picker1, oldVal, newVal) -> {
            if (picker1.getValue() == 0)
                fragmentLayoutBinding.buttonDown.setVisibility(View.INVISIBLE);
            if (picker1.getValue() == floors)
                fragmentLayoutBinding.buttonUp.setVisibility(View.INVISIBLE);
            if (picker1.getValue() != 0)
                fragmentLayoutBinding.buttonDown.setVisibility(View.VISIBLE);
            if (picker1.getValue() != floors)
                fragmentLayoutBinding.buttonUp.setVisibility(View.VISIBLE);
        });
    }

    private void setupPickerLevelWant(NumberPicker picker, int maxFloors, int minValue) {
        picker.setMinValue(minValue);
        picker.setMaxValue(maxFloors);
        picker.setValue(minValue);
    }


}
