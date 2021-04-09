package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.MainFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.ElevatorActivity;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.MainFragmentViewModel;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    MainFragmentLayoutBinding fragmentLayoutBinding;
    private TextView textView;
    private TextView textView2;
    private TextSwitcher textSwitcher;
    private TextSwitcher textSwitcher2;
    private TextView tvSave;

    public MainFragmentViewModel mainFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = MainFragmentLayoutBinding.inflate(inflater, container, false);

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        textSwitcher = fragmentLayoutBinding.textSwitcher;
        textSwitcher2 = fragmentLayoutBinding.textSwitcher2;
        tvSave = fragmentLayoutBinding.tvSave;

        textSwitcher.setFactory(() -> {
            textView = new TextView(getActivity());
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(55);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            return textView;
        });
        textSwitcher2.setFactory(() -> {
            textView2 = new TextView(getActivity());
            textView2.setTextColor(Color.WHITE);
            textView2.setTextSize(30);
            textView2.setGravity(Gravity.CENTER_HORIZONTAL);
            return textView2;
        });

        setConfigurationData(mainFragmentViewModel, fragmentLayoutBinding);

        tvSave.setOnClickListener(v -> {
            Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
            TextView tvElevators = (TextView) textSwitcher.getCurrentView();
            int numberOfElevators = Integer.parseInt((String) tvElevators.getText());
            TextView tvFloors = (TextView) textSwitcher2.getCurrentView();
            int numberOfFloors = Integer.parseInt((String) tvFloors.getText());
            setListOfElevators(numberOfElevators, numberOfFloors);
        });

        return fragmentLayoutBinding.getRoot();
    }

    private void setupPickersAndSwitchers(NumberPicker pickerNumber, NumberPicker pickerLevel, int elev, int floors) {
        pickerNumber.setMinValue(1);
        pickerNumber.setMaxValue(16);
        pickerNumber.setValue(elev);
        pickerNumber.setOnValueChangedListener((picker, oldVal, newVal) -> textSwitcher.setText(String.valueOf(picker.getValue())));
        textSwitcher.setText(String.valueOf(elev));

        pickerLevel.setMinValue(5);
        pickerLevel.setMaxValue(50);
        pickerLevel.setValue(floors);
        pickerLevel.setOnValueChangedListener((picker, oldVal, newVal) -> textSwitcher2.setText(String.valueOf(picker.getValue())));
        textSwitcher2.setText(String.valueOf(floors));
    }

    private void setConfigurationData(MainFragmentViewModel mainFragmentViewModel, MainFragmentLayoutBinding binding) {
        mainFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
            int numberOfElev = 3;
            int numberOfFloors = 10;
            if (!elevatorItems.isEmpty()) {
                numberOfElev = elevatorItems.size();
                numberOfFloors = elevatorItems.get(0).getMaxFloor();
                setupPickersAndSwitchers(binding.pickerNumber, binding.pickerLevel, numberOfElev, numberOfFloors);
            } else {
                setupPickersAndSwitchers(binding.pickerNumber, binding.pickerLevel, numberOfElev, numberOfFloors);
            }
        });
    }

    private void setListOfElevators(int numberOfElevators, int numberOfFloors) {
        mainFragmentViewModel.deleteAllElevators();
        //List<Integer> listRandomFloors = mainFragmentViewModel.getRandomFloors(numberOfElevators, numberOfFloors);
        //for (int item : listRandomFloors) {
        //    System.out.println(item);
        //}
        for (int i = 1; i <= numberOfElevators; i++) {
            mainFragmentViewModel.upsert(new ElevatorItem(i, 0, 0, numberOfFloors, 0));
        }
    }
}
