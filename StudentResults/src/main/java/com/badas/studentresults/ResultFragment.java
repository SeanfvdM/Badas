package com.badas.studentresults;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Project: Student Results
 * By: Seanf
 * Created: 05,September,2020
 */
public class ResultFragment extends Fragment {
    static ArrayList<StudentResult> resultsData = new ArrayList<>();
    static ResultsAdapter resultsAdapter = new ResultsAdapter(resultsData);
    RecyclerView recyclerView;
    FloatingActionButton scrollToTop;
    boolean scrollingToTop = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(Objects.requireNonNull(getContext()).getResources().getLayout(R.layout.result_layout), container, false);
        recyclerView = view.findViewById(R.id.rv_Results);
        scrollToTop = view.findViewById(R.id.scrollTop);
        scrollToTop.setVisibility(View.GONE);
        scrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
                scrollingToTop = true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollOffset() > 0 && !scrollingToTop)
                    scrollToTop.show();
                else
                    scrollToTop.hide();

                if (recyclerView.computeVerticalScrollOffset() == 0)
                    scrollingToTop = false;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }

        resultsAdapter = new ResultsAdapter(resultsData);
        recyclerView.setAdapter(resultsAdapter);
    }

    //animation from https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-1-list-75a874a5d213
    void runLayoutAnimation() {
        try {
            recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.recycler_anim));
            resultsAdapter.notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        } catch (Exception ignored) {

        }
    }

    public void attachDataSet(ArrayList<StudentResult> resultsData) {
        ResultFragment.resultsData = resultsData;
        resultsAdapter.notifyDataSetChanged();
    }

    public static void initDataSet(ArrayList<StudentResult> resultsData) {
        ResultFragment.resultsData = resultsData;
        try {
            resultsAdapter.notifyDataSetChanged();
        } catch (Exception ignored){

        }

    }

    public void dataSetUpdated() {
        resultsAdapter.notifyDataSetChanged();
    }

    public ResultsAdapter getResultsAdapter() {
        return resultsAdapter;
    }

    public ArrayList<StudentResult> getResultsData() {
        return resultsData;
    }

    public static ArrayList<StudentResult> getCurrentResultsData() {
        return resultsData;
    }

    public void addDataToDataSet(StudentResult studentResult) {
        resultsData.add(studentResult);
        resultsAdapter.notifyItemInserted(resultsData.size() - 1);
    }

    public static void prepForGarbageCollection() {
        resultsData = null;
        resultsAdapter = null;
    }
}
