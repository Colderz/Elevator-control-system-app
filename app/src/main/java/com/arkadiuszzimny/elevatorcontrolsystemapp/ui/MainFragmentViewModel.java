package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private ElevatorRepository repository;
    private LiveData<List<ElevatorItem>> allElevators;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new ElevatorRepository(application);
        allElevators = repository.getAllElevators();
    }

    public void upsert(ElevatorItem item) {
        repository.upsert(item);
    }

    public void deleteAllElevators() {
        repository.deleteAllElevators();
    }

    public LiveData<List<ElevatorItem>> getAllElevators() {
        return allElevators;
    }

}
