package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String course_code;
    private String course_name;
    private School school;

    // holds a list of indexes that this course has
    private ArrayList<Index> indexes;

    public Course(String course_code, String course_name, School school) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.school = school;
        this.indexes = new ArrayList<>();
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public ArrayList<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(ArrayList<Index> indexes) {
        this.indexes = indexes;
    }
}