package com.test.samplelivedatawithroom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by saket.shriwas on 11/17/2017.
 */
/*
This is the Student Entity class. It will be used by Dao and Room DB to provide Data to the UI.
 */
@Entity(tableName = "student_master")
public class StudentEntity {

    @PrimaryKey(autoGenerate = true)
    public int studentId;
    public String studentName;
    public int age;
    public int student_class;
    public String div;
}
