package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainFragmentViewModel extends AndroidViewModel {
    private ElevatorRepository repository;
    private LiveData<List<ElevatorItem>> allElevators;
    private Random rand = new Random();

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new ElevatorRepository(application);
        allElevators = repository.getAllElevators();
    }

    private void upsert(ElevatorItem item) {
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

    public void updateItem(int id, int currLevel, int maxLevel, int state) {
        ArrayList<String> orderLevels = new ArrayList<>();
        if(state == -1 && currLevel == 0) {
            state = 0;
        }
        if(state == 1 && currLevel == maxLevel) {
            state = 0;
        }
        if(orderLevels.size() > 0) orderLevels.clear();
        switch (state) {
            case -1:
                int ordersDown = rand.nextInt(6)+1;
                for(int i = 0; i<ordersDown; i++) {
                    orderLevels.add(String.valueOf(rand.nextInt(currLevel)));
                }
                break;
            case 1:
                int ordersUp = rand.nextInt(6)+1;
                for(int i = 0; i<ordersUp; i++) {
                    int a = currLevel + rand.nextInt(maxLevel - currLevel)+1;
                    orderLevels.add(String.valueOf(a));
                }
                break;
        }
        if(!(orderLevels.size()>0)) {
            orderLevels.add("-1");
        }
        upsert(new ElevatorItem(id, orderLevels, currLevel, maxLevel, state));
    }

}
