package com.badas.studentresults;

import java.util.ArrayList;

/**
 * Project: Student Results
 * By: Seanf
 * Created: 05,September,2020
 */
public class StudentResult {
    private String Student, Header;
    private ArrayList<ResultData> resultsData = new ArrayList<>(3);

    public String getHeader() {
        return Header;
    }

    public StudentResult setHeader(String header) {
        Header = header;
        return this;
    }

    public String getStudent() {
        return Student;
    }

    public StudentResult setStudent(String student) {
        Student = student;
        return this;
    }

    public ArrayList<ResultData> getResultsData() {
        return resultsData;
    }

    public StudentResult setResultsData(ArrayList<ResultData> resultsData) {
        this.resultsData = resultsData;
        return this;
    }

    public StudentResult Add(String label, String value){
        resultsData.add(new ResultData().setLabel(label).setValue(value));
        return this;
    }

    public static class ResultData{
        private String label, value;

        public ResultData() {
        }

        public ResultData(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public ResultData setLabel(String label) {
            this.label = label;
            return this;
        }

        public String getValue() {
            return value;
        }

        public ResultData setValue(String value) {
            this.value = value;
            return this;
        }
    }
}
