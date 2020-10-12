package com.badas.studentactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Project: ChildActivity
 * By: Brandon
 * Reviewed By: Seanf
 * Created: 11,September,2020
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{
    private ArrayList<Student> students = new ArrayList<>();//changed the dataset type

    public StudentAdapter(ArrayList<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.tv_Name.setText(students.get(position).studentName);
        holder.tv_Date.setText(students.get(position).getDateString());
        holder.tv_Duration.setText(students.get(position).getSessionDurationString());
        //todo add data for tv_extra i.e. decide how you want to display the activity and task complete
    }

    @Override
    public int getItemCount() {
//        return 0;//note if you leave this to return 0 no items will displayed
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_Name,tv_Date,tv_Duration,tv_Extra;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_studentName);
            tv_Date = itemView.findViewById(R.id.tv_date);
            tv_Duration = itemView.findViewById(R.id.tv_duration);
            tv_Extra = itemView.findViewById(R.id.tv_extra);
        }
    }

}
