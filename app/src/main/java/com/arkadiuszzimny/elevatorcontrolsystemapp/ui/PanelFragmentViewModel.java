package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.ArrayList;
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

    public void stepSimulation(List<ElevatorItem> elevatorItems) {
            upsert(new ElevatorItem(1, Integer.parseInt(String.valueOf(elevatorItems.get(0).getTargetFloors().get(0))), step(elevatorItems.get(0).getCurrentFloor(), elevatorItems.get(0).getTargetFloors()), elevatorItems.get(0).getMaxFloor(), setState(elevatorItems)));
            //step(elevatorItems.get(0).getCurrentFloor(), elevatorItems.get(0).getTargetFloors());
    }

    private ArrayList<String> step(int currLevel, ArrayList<String> queue) {
        if(queue.size()>=1) {
            queue.remove(0);
        }
        if(queue.size()==0) {
            queue.add("-1");
        }
        return queue;
    }

    private int setState(List<ElevatorItem> elevatorItems) {
        if(String.valueOf(elevatorItems.get(0).getTargetFloors().get(0)).equals("-1")) return 0;
        else return elevatorItems.get(0).getState();
    }


}
