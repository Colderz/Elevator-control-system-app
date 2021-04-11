package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.ElevatorRepository;
import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PanelFragmentViewModel extends AndroidViewModel {
    private ElevatorRepository repository;
    private LiveData<List<ElevatorItem>> allElevators;
    private int orderedElevatorId = -1;
    private int orderedFloor = -1;

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
        for (ElevatorItem item : elevatorItems) {
            if (item.getState() == 0 && String.valueOf(item.getTargetFloors().get(0)).equals("-1")) {
                upsert(new ElevatorItem(item.getId(), item.getCurrentFloor(), item.getTargetFloors(), item.getMaxFloor(), 0));
            } else {
                upsert(new ElevatorItem(item.getId(), Integer.parseInt(String.valueOf(item.getTargetFloors().get(0))), step(item.getTargetFloors()), item.getMaxFloor(), setState(item)));
            }
        }
    }

    private ArrayList<String> step(ArrayList<String> queue) {
        if (queue.size() >= 1) {
            queue.remove(0);
        }
        if (queue.size() == 0) {
            queue.add("-1");
        }
        return queue;
    }

    private int setState(ElevatorItem item) {
        if (String.valueOf(item.getTargetFloors().get(0)).equals("-1")) return 0;
        else return item.getState();
    }


    public int getNearestElevator(List<ElevatorItem> elevators, int direction, int requestFloor) {
        List<Integer> distances = new ArrayList<>();
        int elevatorID = -1;
        if (direction == 1) {
            //Filtered list with elevators going up below our floor and elevators standing anywhere
            List<ElevatorItem> filteredList = elevators.stream().filter(e -> (e.getState() == 1 && e.getCurrentFloor() <= requestFloor) || e.getState() == 0).collect(Collectors.toList());
            filteredList.forEach(elevatorItem -> distances.add(Math.abs(requestFloor - elevatorItem.getCurrentFloor())));
            int min = Collections.min(distances);
            elevatorID = elevators.stream().filter(elevatorItem -> Math.abs(requestFloor - elevatorItem.getCurrentFloor()) == min).collect(Collectors.toList()).get(0).getId();
        }
        if (direction == -1) {
            //Filtered list with elevators going above our floor and elevators standing anywhere
            List<ElevatorItem> filteredList = elevators.stream().filter(e -> (e.getState() == -1 && e.getCurrentFloor() >= requestFloor) || e.getState() == 0).collect(Collectors.toList());
            filteredList.forEach(elevatorItem -> System.out.println(elevatorItem.getId()));
            filteredList.forEach(elevatorItem -> distances.add(Math.abs(requestFloor - elevatorItem.getCurrentFloor())));
            int min = Collections.min(distances);
            elevatorID = elevators.stream().filter(elevatorItem -> Math.abs(elevatorItem.getCurrentFloor() - requestFloor) == min).collect(Collectors.toList()).get(0).getId();
        }
        orderedElevatorId = elevatorID;
        orderedFloor = requestFloor;
        return elevatorID;
    }

    public void addToQueue(List<ElevatorItem> elevators, int direction, int nearestElevatorId, int requestFloor) {
        int currentFloor = elevators.get(nearestElevatorId - 1).getCurrentFloor();
        int maxFloor = elevators.get(0).getMaxFloor();
        int forceDirection = 0;
        if (direction == 0) {
            forceDirection = elevators.get(nearestElevatorId - 1).getCurrentFloor() > requestFloor ? -1 : 1;
        } else {
            forceDirection = direction;
        }
        ArrayList<String> sortedList = getSortedList(elevators, nearestElevatorId, forceDirection, requestFloor);
        upsert(new ElevatorItem(nearestElevatorId, currentFloor, sortedList, maxFloor, forceDirection));
    }

    public boolean orderFloorReachedState(List<ElevatorItem> elevators) {
        if (elevators.get(orderedElevatorId-1).getCurrentFloor() == orderedFloor) return true;
        else return false;
    }

    public void addWantLevelToQueue(List<ElevatorItem> elevators, int desireDirection, int pickerLevelWant) {
        int currentFloor = orderedFloor;
        int maxFloor = elevators.get(0).getMaxFloor();
        ArrayList<String> sortedList = getSortedList(elevators, orderedElevatorId, desireDirection, pickerLevelWant);
        upsert(new ElevatorItem(orderedElevatorId, currentFloor, sortedList, maxFloor, desireDirection));
    }

    private ArrayList<String> getSortedList(List<ElevatorItem> elevators, int elevatorId, int elevatorDirection, int requestFloor) {
        ArrayList<ArrayList<String>> arrayOfTargets = new ArrayList<>();
        for (ElevatorItem elevator : elevators) {
            arrayOfTargets.add(elevator.getTargetFloors());
        }
        arrayOfTargets.get(elevatorId - 1).add(String.valueOf(requestFloor));
        if (String.valueOf(arrayOfTargets.get(elevatorId - 1).get(0)).equals("-1")) {
            arrayOfTargets.get(elevatorId - 1).remove(0);
        }
        ArrayList<String> s = arrayOfTargets.get(elevatorId - 1);
        List<Integer> queueToSort = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            queueToSort.add(Integer.parseInt(String.valueOf(s.get(i))));
        }
        Set<Integer> set = new HashSet<>(queueToSort);
        queueToSort.clear();
        queueToSort.addAll(set);
        if (elevatorDirection == 1) {
            Collections.sort(queueToSort);
        } else if (elevatorDirection == -1) {
            Collections.sort(queueToSort, Collections.reverseOrder());
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (Integer integer : queueToSort) {
            sortedList.add(String.valueOf(integer));
        }
        return sortedList;
    }
}
