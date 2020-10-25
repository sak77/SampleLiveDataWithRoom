package com.test.samplelivedatawithroom;

import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

/*
The purpose of this app is to demonstrate the use of LiveData with Room DB
We will be creating a Student Entity class which will store and display student data.
Later we might even use React native to fetch data from API.
 */
public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        StudentListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //Add Home Fragment to Activity
        if (getSupportFragmentManager().getFragments().size()==0){
            fragmentTransaction.add(R.id.fragment_container,HomeFragment.newInstance("",""));
            fragmentTransaction.add(R.id.fragment2_container,StudentListFragment.newInstance(1), "Student");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
/*

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
*/

    @Override
    public void onListFragmentInteraction(StudentEntity item) {

        if (item==null){
            //Refresh the adapter
            final StudentListFragment studentListFragment = (StudentListFragment) getSupportFragmentManager().findFragmentByTag("Student");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    studentListFragment.updateList();
                }
            });

        }else {
            //Display details
            Toast.makeText(this,"Name: "+item.studentName+"\nAge: "+item.age+"\nClass: "+item.student_class+"\nDiv: "+item.div,Toast.LENGTH_SHORT).show();
        }
    }
}
