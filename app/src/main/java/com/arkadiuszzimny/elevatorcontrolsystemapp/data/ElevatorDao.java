package com.arkadiuszzimny.elevatorcontrolsystemapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.List;

@Dao
public interface ElevatorDao {

    //update or insert = upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(ElevatorItem item);

    @Query("DELETE FROM elevators_table")
    void deleteAllElevators();

    @Query("SELECT * FROM elevators_table ORDER BY id ASC")
    LiveData<List<ElevatorItem>> getAllElevators();

}
