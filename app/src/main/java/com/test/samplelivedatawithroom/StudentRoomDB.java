package com.test.samplelivedatawithroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by saket.shriwas on 11/17/2017.
 */

/*
The StudentRoomDB instance is an abstract class which contains the DB. It has a static method for each Dao
 */
@Database(entities = StudentEntity.class,version = 1)
public abstract class StudentRoomDB extends RoomDatabase {
    //Abstract method to return instance of student Dao
    public abstract StudentDao getStudentDao();
}
