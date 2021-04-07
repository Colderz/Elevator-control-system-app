package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;


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
import android.widget.ViewSwitcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.MainFragmentLayoutBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.MainFragmentViewModel;

public class MainFragment extends Fragment {

    MainFragmentLayoutBinding fragmentLayoutBinding;
    private int stringIndex = 3;
    private int stringIndex2 = 10;
    private TextView textView;
    private TextView textView2;

    public MainFragmentViewModel mainFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = MainFragmentLayoutBinding.inflate(inflater, container, false);

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);


        NumberPicker pickerNumber = fragmentLayoutBinding.pickerNumber;
        NumberPicker pickerLevel = fragmentLayoutBinding.pickerLevel;
        TextSwitcher textSwitcher = fragmentLayoutBinding.textSwitcher;
        TextSwitcher textSwitcher2 = fragmentLayoutBinding.textSwitcher2;
        TextView tvSave = fragmentLayoutBinding.tvSave;

        setupPickersAndSwitchers(pickerNumber, pickerLevel, textSwitcher, textSwitcher2);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "CONFIGURATION SAVED", Toast.LENGTH_SHORT).show();
                TextView tvElevators = (TextView) textSwitcher.getCurrentView();
                int numberOfElevators = Integer.parseInt((String) tvElevators.getText());
                TextView tvFloors = (TextView) textSwitcher2.getCurrentView();
                int numberOfFloors = Integer.parseInt((String) tvFloors.getText());
                mainFragmentViewModel.deleteAllElevators();
                for(int i = 1; i<=numberOfElevators; i++) {
                    mainFragmentViewModel.upsert(new ElevatorItem(i, 0, 0, numberOfFloors));
                }
            }
        });

        return fragmentLayoutBinding.getRoot();
    }

    private void setupPickersAndSwitchers(NumberPicker pickerNumber, NumberPicker pickerLevel, TextSwitcher textSwitcher, TextSwitcher textSwitcher2) {
        pickerNumber.setMinValue(1);
        pickerNumber.setMaxValue(16);
        pickerNumber.setValue(3);
        pickerNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textSwitcher.setText(String.valueOf(picker.getValue()));
            }
        });
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(getActivity());
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(55);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                return textView;
            }
        });
        textSwitcher.setText(String.valueOf(stringIndex));

        pickerLevel.setMinValue(5);
        pickerLevel.setMaxValue(50);
        pickerLevel.setValue(10);
        pickerLevel.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textSwitcher2.setText(String.valueOf(picker.getValue()));
            }
        });

        textSwitcher2.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView2 = new TextView(getActivity());
                textView2.setTextColor(Color.WHITE);
                textView2.setTextSize(30);
                textView2.setGravity(Gravity.CENTER_HORIZONTAL);
                return textView2;
            }
        });
        textSwitcher2.setText(String.valueOf(stringIndex2));
    }



}
