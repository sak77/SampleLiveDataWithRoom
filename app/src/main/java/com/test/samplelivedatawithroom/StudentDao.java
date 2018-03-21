package com.test.samplelivedatawithroom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by saket.shriwas on 11/17/2017.
 */

/*
The Dao provides convenient methods and query methods to perform DB operations. The Dao class can be an interface or abstract class.
If its an abstract class it has an optionall constrcutor with RoomDB instance as parameter.
 */
@Dao
public interface StudentDao {

    //Query to insert Student data
    @Insert
    public long insertStudent(StudentEntity studentEntity);

    //Query to get all students list
    @Query("SELECT * FROM student_master")
    public LiveData<List<StudentEntity>> getAllStudents();
}
