package com.test.samplelivedatawithroom;

import android.app.Application;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by saket.shriwas on 11/17/2017.
 */

public class MyApplicationClass extends Application {

    public StudentRoomDB studentRoomDB;
    @Override
    public void onCreate() {
        super.onCreate();
        createDB();
    }

    private void createDB(){
        studentRoomDB = Room.databaseBuilder(getApplicationContext(),StudentRoomDB.class,"Student_db").build();
    }
}
