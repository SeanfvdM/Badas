package com.badas.studentresults;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Project: Student Results
 * By: Seanf
 * Created: 05,September,2020
 */
public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {
    public ArrayList<StudentResult> getResultsData() {
        return resultsData;
    }

    public void setResultsData(ArrayList<StudentResult> resultsData) {
        this.resultsData = resultsData;
    }

    ArrayList<StudentResult> resultsData;

    public ResultsAdapter(ArrayList<StudentResult> resultsData) {
        this.resultsData = resultsData;
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_card, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        holder.addData(resultsData.get(position)).header.setText(resultsData.get(position).getHeader());
    }

    @Override
    public int getItemCount() {
        return resultsData.size();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        private LinearLayout holder;
        private View view;
        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            header = itemView.findViewById(R.id.tv_CardHeader);
            holder = itemView.findViewById(R.id.ll_dataItemHolder);
        }

        public ResultsViewHolder addData(StudentResult studentResult){
            holder.removeAllViews();
            if(studentResult.getResultsData().size() == 0) {
                holder.setVisibility(View.GONE);
            } else if(holder.getChildCount() < studentResult.getResultsData().size()){
                View child;
                for (StudentResult.ResultData data:studentResult.getResultsData()) {
                    child = LayoutInflater.from(view.getContext()).inflate(R.layout.card_data_item, holder,false);
                    if(!TextUtils.isEmpty(data.getLabel()))
                        ((TextView)child.findViewById(R.id.tv_CardText)).setText(data.getLabel());
                    if(!TextUtils.isEmpty(data.getValue()))
                        ((TextView)child.findViewById(R.id.tv_CardValue)).setText(data.getValue());
                    child.setId(data.hashCode());
                    holder.addView(child);
                }
            }
            return this;
        }
    }
}
