package com.arkadiuszzimny.elevatorcontrolsystemapp.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * TypeConverter napisany w celu określenia sposobu odczytu kolejki. Zastosowanie listy dla obiektów typu String w łatwy sposób umożliwia użycie Gson'a.
 */
public class Converters {
    @TypeConverter
    public static ArrayList<String> fromInteger(String value) {
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
