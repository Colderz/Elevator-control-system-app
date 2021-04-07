package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PanelFragmentViewModel extends AndroidViewModel {
    private ElevatorRepository repository;
    private LiveData<List<ElevatorItem>> allElevators;

    public PanelFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new ElevatorRepository(application);
        allElevators = repository.getAllElevators();
    }

    public void upsert(ElevatorItem item) {
        repository.upsert(item);
    }

    public LiveData<List<ElevatorItem>> getAllElevators() {
        return allElevators;
    }

    public List<Integer> generateRandomState(int size) {
        System.out.println("TU JESTEM! ------------");
        List<Integer> listRandomState = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i<size; i++) {
            listRandomState.add(rand.nextInt(3)-1);
        }
        return listRandomState;
    }
}
