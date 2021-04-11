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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.PanelFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.PanelFragmentViewModel;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.adapters.ElevatorRecyclerAdapter;
import com.arkadiuszzimny.elevatorcontrolsystemapp.util.BubbleInterpolator;


public class PanelFragment extends Fragment {

    private PanelFragmentLayoutBinding fragmentLayoutBinding;
    private PanelFragmentViewModel panelFragmentViewModel;
    private ElevatorRecyclerAdapter adapter;
    private Animation bubble;
    private int clickSimulationCounter, maxClickSimulation, clickPickupController, desireDirection, showLevelWantController, counterPickup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = PanelFragmentLayoutBinding.inflate(inflater, container, false);
        panelFragmentViewModel = new ViewModelProvider(this).get(PanelFragmentViewModel.class);

        RecyclerView recyclerView = fragmentLayoutBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        setIntegerValues();
        setMaxClickSimulation();

        adapter = new ElevatorRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        bubble = AnimationUtils.loadAnimation(getActivity(), R.anim.bubble);
        BubbleInterpolator bubbleInterpolator = new BubbleInterpolator(0.2, 20);
        bubble.setInterpolator(bubbleInterpolator);

        panelFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
            if (elevatorItems.size() > 0) {
                int maxFloor = elevatorItems.get(0).getMaxFloor();
                fragmentLayoutBinding.tvFloors.setText(String.valueOf(maxFloor));
                if(counterPickup == 0) {
                    setupPickerFloor(fragmentLayoutBinding.pickerFloor, maxFloor);
                }
                adapter.setElevators(elevatorItems);
            } else {
                Toast.makeText(getActivity(), R.string.warn_data, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentLayoutBinding.simulationButton.setOnClickListener(v -> {
            if (clickPickupController == 1) {
                if (orderFloorReached()) {
                    showLevelWantController = 1;
                    preparePickupCardAgain();
                    Toast.makeText(getActivity(), R.string.lift_info, Toast.LENGTH_LONG).show();
                }
            }
            if (!(clickSimulationCounter > maxClickSimulation)) {
                if (showLevelWantController == 0) {
                    clickSimulationCounter++;
                    panelFragmentViewModel.stepSimulation(adapter.elevators);
                }
                fragmentLayoutBinding.simulationButton.startAnimation(bubble);
            } else {
                Toast.makeText(fragmentLayoutBinding.simulationButton.getContext(), R.string.info_sim_end, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentLayoutBinding.buttonUp.setOnClickListener(v -> {
            counterPickup = 1;
            int requestFloor = fragmentLayoutBinding.pickerFloor.getValue();
            clickSimulationCounter--;
            desireDirection = 1;
            if (clickPickupController == 0) {
                fragmentLayoutBinding.buttonUp.setAlpha(0.2F);
                fragmentLayoutBinding.buttonUp.setClickable(false);
                fragmentLayoutBinding.buttonDown.setClickable(false);
            }
            setupPickerLevelWant(fragmentLayoutBinding.pickerLevelWant, Integer.parseInt(String.valueOf(fragmentLayoutBinding.tvFloors.getText())), fragmentLayoutBinding.pickerFloor.getValue());
            callNearestElevator(1, requestFloor);
            fragmentLayoutBinding.buttonUp.startAnimation(bubble);
            clickPickupController = 1;
            fragmentLayoutBinding.pickerFloor.setValue(requestFloor);
        });

        fragmentLayoutBinding.buttonDown.setOnClickListener(v -> {
            counterPickup = 1;
            int requestFloor = fragmentLayoutBinding.pickerFloor.getValue();
            clickSimulationCounter--;
            desireDirection = -1;
            if (clickPickupController == 0) {
                fragmentLayoutBinding.buttonDown.setAlpha(0.2F);
                fragmentLayoutBinding.buttonUp.setClickable(false);
                fragmentLayoutBinding.buttonDown.setClickable(false);
            }
            setupPickerLevelWant(fragmentLayoutBinding.pickerLevelWant, fragmentLayoutBinding.pickerFloor.getValue(), 0);
            callNearestElevator(-1, requestFloor);
            fragmentLayoutBinding.buttonDown.startAnimation(bubble);
            clickPickupController = 1;
            fragmentLayoutBinding.pickerFloor.setValue(requestFloor);
        });


        fragmentLayoutBinding.buttonChoose.setOnClickListener(v -> {
            hideLevelWantPicker();
            showFloorPicker();
            showLevelWantController = 0;
            counterPickup = 0;
            panelFragmentViewModel.addWantLevelToQueue(adapter.elevators, desireDirection, fragmentLayoutBinding.pickerLevelWant.getValue());
        });

        return fragmentLayoutBinding.getRoot();
    }

    private boolean orderFloorReached() {
        return panelFragmentViewModel.orderFloorReachedState(adapter.elevators);
    }

    private void preparePickupCardAgain() {
        clickPickupController = 0;
        hideFloorPicker();
        showLevelWantPicker();
        fragmentLayoutBinding.buttonUp.setAlpha(1F);
        fragmentLayoutBinding.buttonDown.setAlpha(1F);
        fragmentLayoutBinding.buttonUp.setClickable(true);
        fragmentLayoutBinding.buttonDown.setClickable(true);
    }

    private void setIntegerValues() {
        clickSimulationCounter = 0;
        clickPickupController = 0;
        desireDirection = 0;
        showLevelWantController = 0;
        counterPickup = 0;
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

    private void setMaxClickSimulation() {
        panelFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> elevatorItems.forEach(elevatorItem -> {
            if (elevatorItem.getTargetFloors().size() > maxClickSimulation)
                maxClickSimulation = elevatorItem.getTargetFloors().size();
        }));
    }

    private void callNearestElevator(int direction, int requestFloor) {
        int nearestElevatorId = panelFragmentViewModel.getNearestElevator(adapter.elevators, direction, requestFloor);
        if (nearestElevatorId != -1)
            Toast.makeText(getActivity(), "Elevator number " + nearestElevatorId + " will arrive", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(), R.string.wait, Toast.LENGTH_LONG).show();
        addOrderToQueue(nearestElevatorId, requestFloor);
    }

    private void addOrderToQueue(int nearestElevatorId, int requestFloor) {
        int direction = adapter.elevators.get(nearestElevatorId - 1).getState();
        panelFragmentViewModel.addToQueue(adapter.elevators, direction, nearestElevatorId, requestFloor);
    }

}
