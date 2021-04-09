package com.arkadiuszzimny.elevatorcontrolsystemapp.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.arkadiuszzimny.elevatorcontrolsystemapp.data.entities.ElevatorItem;

@Database(entities = {ElevatorItem.class}, version = 3, exportSchema = false)
public abstract class ElevatorDatabase extends RoomDatabase {

    private static ElevatorDatabase instance;

    public abstract ElevatorDao elevatorDao();

    public static synchronized ElevatorDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ElevatorDatabase.class, "elevator_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ElevatorDao elevatorDao;

        private PopulateDbAsyncTask(ElevatorDatabase db) {
            elevatorDao = db.elevatorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            elevatorDao.upsert(new ElevatorItem(1, 5, 1, 20, 0));
            return null;
        }
    }

}
