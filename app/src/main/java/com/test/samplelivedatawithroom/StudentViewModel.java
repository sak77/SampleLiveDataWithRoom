package com.test.samplelivedatawithroom;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by saket.shriwas on 11/17/2017.
 */

/*
The ViewModel class provides data to UI components such as activity/fragment.
It is un-affected by activity configuration changes that occur when activity or fragment is re-created on device rotation.
 */

public class StudentViewModel extends ViewModel {
    //Trying to observe the list of students used to populate the listview in fragment2
    //private MutableLiveData<List<StudentEntity>> mStudents;

    private StudentDao studentDao;

    //Init students list
    public void init(StudentRoomDB studentRoomDB){
        if (studentDao==null){
            studentDao = studentRoomDB.getStudentDao();
        }
    }

    //Method will add Student to the existing DB
    public long addStudent(StudentEntity mStudent){
        //Using Dao insert
        return studentDao.insertStudent(mStudent);
    }

    public LiveData<List<StudentEntity>> getmStudents() {
        return studentDao.getAllStudents();
    }
}
