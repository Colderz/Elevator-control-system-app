package com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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
import com.arkadiuszzimny.elevatorcontrolsystemapp.util.BubbleInterpolator;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa fragmentu dla widoku konfiguracji systemu połączona z dostarczonym dedykowanym ViewModelem
 */
public class MainFragment extends Fragment {

    private MainFragmentLayoutBinding fragmentLayoutBinding;
    private TextView textView;
    private TextView textView2;
    private TextSwitcher textSwitcher;
    private TextSwitcher textSwitcher2;
    private FrameLayout saveButton;
    private Animation bubble;
    private BubbleInterpolator bubbleInterpolator;

    public MainFragmentViewModel mainFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayoutBinding = MainFragmentLayoutBinding.inflate(inflater, container, false);

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        textSwitcher = fragmentLayoutBinding.textSwitcher;
        textSwitcher2 = fragmentLayoutBinding.textSwitcher2;
        saveButton = fragmentLayoutBinding.saveButton;
        bubble = AnimationUtils.loadAnimation(getActivity(), R.anim.bubble);
        bubbleInterpolator = new BubbleInterpolator(0.2, 20);
        bubble.setInterpolator(bubbleInterpolator);

        fragmentLayoutBinding.textSwitcher.setFactory(() -> {
            textView = new TextView(getActivity());
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(55);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            return textView;
        });
        fragmentLayoutBinding.textSwitcher2.setFactory(() -> {
            textView2 = new TextView(getActivity());
            textView2.setTextColor(Color.WHITE);
            textView2.setTextSize(30);
            textView2.setGravity(Gravity.CENTER_HORIZONTAL);
            return textView2;
        });

        setConfigurationData();

        saveButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
            TextView tvElevators = (TextView) textSwitcher.getCurrentView();
            int numberOfElevators = Integer.parseInt((String) tvElevators.getText());
            TextView tvFloors = (TextView) textSwitcher2.getCurrentView();
            int numberOfFloors = Integer.parseInt((String) tvFloors.getText());
            setListOfElevators(numberOfElevators, numberOfFloors);
            saveButton.startAnimation(bubble);
        });

        return fragmentLayoutBinding.getRoot();
    }

    /**
     * Metoda ustawiająca wartości dla obu pickerów w MainFragment oraz dla obu textSwitcherów
     * @param elev opisuje liczbę wind w systemie
     * @param floors opisuje liczbę pięter w systemie
     */
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

    /**
     * Metoda używa obserwatora dla listy, aby uniknąć błędu ustawiania danych dla jeszcze nie zainicjalizowanej tablicy.
     * Oba pickery oraz textSwitchery ustawione są na domyślne wartości 3 oraz 10, lecz jest to ustawienie całkowicie losowe i nic nie znaczące.
     * Proste sprawdzenie czy lista posiada elementy umożliwia nam wywołanie metody setupPickersAndSwitchers.
     * Metoda nie przyjmuje żadnych argumentów.
     */
    private void setConfigurationData() {
        mainFragmentViewModel.getAllElevators().observe(getActivity(), elevatorItems -> {
            int numberOfElev = 3;
            int numberOfFloors = 10;
            if (!elevatorItems.isEmpty()) {
                numberOfElev = elevatorItems.size();
                numberOfFloors = elevatorItems.get(0).getMaxFloor();
                setupPickersAndSwitchers(fragmentLayoutBinding.pickerNumber, fragmentLayoutBinding.pickerLevel, numberOfElev, numberOfFloors);
            } else {
                setupPickersAndSwitchers(fragmentLayoutBinding.pickerNumber, fragmentLayoutBinding.pickerLevel, numberOfElev, numberOfFloors);
            }
        });
    }

    /**
     * Metoda inicjalizująca stworzenie listy wind oraz wygenerowanie danych startowych symulacji za pomocą metod generateRandomState oraz generateRandomLevel dla każdej z wind.
     * @param numberOfElevators
     * @param numberOfFloors
     */
    private void setListOfElevators(int numberOfElevators, int numberOfFloors) {
        mainFragmentViewModel.deleteAllElevators();
        for (int i = 1; i <= numberOfElevators; i++) {
            int state = mainFragmentViewModel.generateRandomState();
            int currLevel = mainFragmentViewModel.generateRandomLevel(numberOfFloors);
            mainFragmentViewModel.updateItem(i, currLevel, numberOfFloors, state);
        }
    }
}
