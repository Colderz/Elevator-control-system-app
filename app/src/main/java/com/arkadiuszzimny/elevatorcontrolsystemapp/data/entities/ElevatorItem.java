package com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Encja bazy danych opisująca potrzebna do opisu stanu pojedynczej windy. Intuicyjnie są to ID windy, obecne piętro, kolejka pięter oraz stan windy/kierunek poruszania
 * Określenie sposobu odczytu targetFloors wymagała zastosowania Type Convertera
 */
@Entity(tableName = "elevators_table")
public class ElevatorItem {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "current_floor")
    private int currentFloor;
    @ColumnInfo(name = "target_floors")
    private ArrayList<String> targetFloors;
    @ColumnInfo(name = "max_floor")
    private int maxFloor;
    @ColumnInfo(name = "state")
    private int state;

    public ElevatorItem(int id, int currentFloor, ArrayList<String> targetFloors, int maxFloor, int state) {
        this.id = id;
        this.targetFloors = targetFloors;
        this.currentFloor = currentFloor;
        this.maxFloor = maxFloor;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getTargetFloors() {
        return targetFloors;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getState() { return state; }

}
