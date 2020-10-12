package com.badas.studentactivity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Project: ChildActivity
 * By: Brandon
 * Reviewed By: Seanf
 * Created: 11,September,2020
 */
public class StudentActivityFragment extends Fragment {

    private static ArrayList<Student> students = new ArrayList<>();
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_activity, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        studentAdapter = new StudentAdapter(students);
        recyclerView.setAdapter(studentAdapter);
        return view;
    }

    public void attachDataSet(ArrayList<Student> students){
        StudentActivityFragment.students = students;
        try{
            studentAdapter = new StudentAdapter(students);
            studentAdapter.notifyDataSetChanged();
        } catch (Exception ignored){

        }
    }

    public static void initDataSet(ArrayList<Student> students){
        StudentActivityFragment.students = students;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }
}