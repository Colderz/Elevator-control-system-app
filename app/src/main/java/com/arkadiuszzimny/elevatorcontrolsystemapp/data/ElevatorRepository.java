package com.arkadiuszzimny.elevatorcontrolsystemapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

import java.util.List;

public class ElevatorRepository {

    private ElevatorDao elevatorDao;
    private LiveData<List<ElevatorItem>> allElevators;


    public ElevatorRepository(Application application) {
        ElevatorDatabase database = ElevatorDatabase.getInstance(application);
        elevatorDao = database.elevatorDao();
        allElevators = elevatorDao.getAllElevators();
    }

    public void upsert(ElevatorItem item) {
        new UpsertElevatorAsyncTask(elevatorDao).execute(item);
    }

    public void deleteAllElevators() {
        new DeleteAllElevatorsAsyncTask(elevatorDao).execute();
    }

    public LiveData<List<ElevatorItem>> getAllElevators() {
        return allElevators;
    }

    private static class UpsertElevatorAsyncTask extends AsyncTask<ElevatorItem, Void, Void> {
        private ElevatorDao elevatorDao;

        private UpsertElevatorAsyncTask(ElevatorDao elevatorDao) {
            this.elevatorDao = elevatorDao;
        }

        @Override
        protected Void doInBackground(ElevatorItem... elevatorItems) {
            elevatorDao.upsert(elevatorItems[0]);
            return null;
        }
    }

    private static class DeleteAllElevatorsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ElevatorDao elevatorDao;

        private DeleteAllElevatorsAsyncTask(ElevatorDao elevatorDao) {
            this.elevatorDao = elevatorDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            elevatorDao.deleteAllElevators();
            return null;
        }
    }


}
