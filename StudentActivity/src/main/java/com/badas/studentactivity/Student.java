package com.badas.studentactivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Project: ChildActivity
 * By: Brandon
 * Reviewed By: Seanf
 * Created: 11,September,2020
 */
public class Student {
    //renamed the variables
    String studentName;
    Date date;
    Long sessionDuration;
    String taskComplete;
    String activity;

    //added get string for date and duration

    public Student (){

    }

    public Student(String studentName, Date date, Long sessionDuration, String taskComplete, String activity) {
        this.studentName = studentName;
        this.date = date;
        this.sessionDuration = sessionDuration;
        this.taskComplete = taskComplete;
        this.activity = activity;
    }

    public String getStudentName() {
        return studentName;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString(){
        return new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(date);
    }

    public Long getSessionDuration() {
        return sessionDuration;
    }

    public String getSessionDurationString(){
        //https://stackoverflow.com/a/266970
        long seconds = sessionDuration/1000;
        return String.format(Locale.getDefault(),"%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }

    public String getTaskComplete() {
        return taskComplete;
    }

    public String getActivity() {
        return activity;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSessionDuration(Long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public void setTaskComplete(String taskComplete) {
        this.taskComplete = taskComplete;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
