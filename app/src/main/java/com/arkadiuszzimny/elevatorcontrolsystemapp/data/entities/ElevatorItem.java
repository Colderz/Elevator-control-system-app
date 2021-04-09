package com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "elevators_table")
public class ElevatorItem {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "target_floor")
    private int targetFloor;
    @ColumnInfo(name = "current_floor")
    private int currentFloor;
    @ColumnInfo(name = "max_floor")
    private int maxFloor;
    @ColumnInfo(name = "state")
    private int state;

    public ElevatorItem(int id, int targetFloor, int currentFloor, int maxFloor, int state) {
        this.id = id;
        this.targetFloor = targetFloor;
        this.currentFloor = currentFloor;
        this.maxFloor = maxFloor;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getState() { return state; }
}
