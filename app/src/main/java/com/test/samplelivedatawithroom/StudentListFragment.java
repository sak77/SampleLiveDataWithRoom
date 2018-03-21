package com.test.samplelivedatawithroom;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.samplelivedatawithroom.dummy.DummyContent;
import com.test.samplelivedatawithroom.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StudentListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    List<StudentEntity> lstStudents;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StudentListFragment newInstance(int columnCount) {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studententity_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyStudentEntityRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            //Get list of students
            StudentViewModel studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
            StudentRoomDB roomDB = ((MyApplicationClass)getActivity().getApplication()).studentRoomDB;
            studentViewModel.init(roomDB);
            LiveData<List<StudentEntity>> live_lstStudents = studentViewModel.getmStudents();
                    //LiveData<List<StudentEntity>> lstStudents = ViewModelProviders.of(this).get(StudentViewModel.class).getmStudents();
            lstStudents = live_lstStudents .getValue();

            if (lstStudents == null){
                lstStudents = new ArrayList<StudentEntity>();
            }

            Observer<List<StudentEntity>> myObserver = new Observer<List<StudentEntity>>() {
                @Override
                public void onChanged(@Nullable List<StudentEntity> studentEntities) {
                    //Update Listview adapter
                    lstStudents = studentEntities;

                    if (recyclerView.getAdapter()==null){
                        MyStudentEntityRecyclerViewAdapter  recyclerViewAdapter = new MyStudentEntityRecyclerViewAdapter(lstStudents, mListener);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }else {
                        if (Looper.myLooper() ==  Looper.getMainLooper()){
                            //Temporary workaround untill i find a way to get the notifyDatasetChanged to work.
                            MyStudentEntityRecyclerViewAdapter  recyclerViewAdapter = new MyStudentEntityRecyclerViewAdapter(lstStudents, mListener);
                            recyclerView.setAdapter(recyclerViewAdapter);

                            //recyclerView.getAdapter().notifyItemInserted(lstStudents.size());
                            //recyclerView.getAdapter().notifyDataSetChanged(); //--- This does not work
                            Toast.makeText(getContext(),"New record added",Toast.LENGTH_SHORT).show();
                        }else {
                            //Notify listener -
                            mListener.onListFragmentInteraction(null);
                        }
                    }
                }
            };
            studentViewModel.getmStudents().observe(this,myObserver);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(StudentEntity item);
    }

    public void updateList(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void addStudent(StudentEntity studentEntity){
        lstStudents.add(studentEntity);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
