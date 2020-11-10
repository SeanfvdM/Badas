package com.badas.studentresults;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Project: Student Results
 * By: Seanf
 * Created: 05,September,2020
 */
public class StudentResultFragment extends ResultFragment {
    static ArrayList<StudentResult> currentResultsData = new ArrayList<>();
    static ArrayAdapter<String> studentAdapter;
    static ArrayList<String> students = new ArrayList<>();
    Spinner spinner;

    public StudentResultFragment() {
        resultsAdapter = new ResultsAdapter(currentResultsData);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadCurrent();
        View view = inflater.inflate(Objects.requireNonNull(getContext()).getResources().getLayout(R.layout.student_result_layout), container, false);
        recyclerView = view.findViewById(R.id.rv_Results);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_up));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
                scrollingToTop = true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_IDLE)
                    scrollingToTop = false;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollOffset() > 0 && !scrollingToTop)
                    floatingActionButton.show();
                else
                    floatingActionButton.hide();
            }
        });
        spinner = view.findViewById(R.id.spinner);
        studentAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_text, students);
        spinner.setAdapter(studentAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadCurrent();
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        resultsAdapter = new ResultsAdapter(currentResultsData);
        recyclerView.setAdapter(resultsAdapter);
    }

    public static void prepForGarbageCollection(){
        ResultFragment.prepForGarbageCollection();
        currentResultsData = null;
        studentAdapter = null;
        students = null;
    }

    private void loadCurrent(){
        students = new ArrayList<>();
        for (StudentResult data:resultsData) {
            if (!students.contains(data.getStudent()) && data.getStudent() != null){
                students.add(data.getStudent());
            }
        }
        currentResultsData = new ArrayList<>();
        try{
            for (StudentResult data:resultsData) {
                if (!currentResultsData.contains(data) && data.getStudent().equals(spinner.getSelectedItem().toString())){
                    currentResultsData.add(data);
                }
            }
        } catch (Exception ignored){

        }
        try{
            studentAdapter.notifyDataSetChanged();
        } catch (NullPointerException ignored) {
        }
        resultsAdapter.setResultsData(currentResultsData);
        runLayoutAnimation();
    }

    @Override
    public void attachDataSet(@NotNull ArrayList<StudentResult> resultsData){
        StudentResultFragment.resultsData = resultsData;
        loadCurrent();
    }

    @Override
    public void dataSetUpdated(){
        runLayoutAnimation();
        try{
            studentAdapter.notifyDataSetChanged();
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public void addDataToDataSet(StudentResult StudentResult){
        resultsData.add(StudentResult);
        resultsAdapter.notifyItemInserted(resultsData.size()-1);
        loadCurrent();
    }
}
