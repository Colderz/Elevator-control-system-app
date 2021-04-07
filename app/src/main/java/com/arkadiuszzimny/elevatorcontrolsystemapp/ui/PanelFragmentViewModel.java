package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.List;

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
}
