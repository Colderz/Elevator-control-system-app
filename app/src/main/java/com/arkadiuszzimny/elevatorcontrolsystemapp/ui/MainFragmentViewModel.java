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

public class MainFragmentViewModel extends AndroidViewModel {
    private ElevatorRepository repository;
    private LiveData<List<ElevatorItem>> allElevators;
    Random rand = new Random();

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

    public int generateRandomState() {
        return (rand.nextInt(3) - 1);
    }

    public int generateRandomLevel(int maxLevel) {
        return rand.nextInt(maxLevel + 1);
    }

    public void updateItem(int id, int targetFloor, int currLevel, int maxLevel, int state) {
        upsert(new ElevatorItem(id, targetFloor, currLevel, maxLevel, state));
    }

    //public List<Integer> getRandomFloors(int numberOfElevators, int numberOfFloors) {
    //    List<Integer> listRandomFloor = new ArrayList<>();
    //    Random rand = new Random();
    //    for(int i = 1; i<=numberOfElevators; i++) {
    //        listRandomFloor.add(rand.nextInt(numberOfFloors+1));
    //    }
    //    return listRandomFloor;
    //}
}
