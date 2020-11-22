package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String course_code;
    private String course_name;
    private School school;
    private int AU;

    // holds a list of indexes that this course has
    private ArrayList<Index> indexes;

    //New Constructor

    /**
     * Constructor for Courses
     * @param course_code course code of each modules offered e.g(CZ002)
     * @param course_name course name of ech modules offered e.g(Object Oriented Programming)
     * @param school School of which course is located e.g(SCSE, MAE)
     * @param AU Allocated Unit for each course
     */

    public Course(String course_code, String course_name, School school, int AU) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.school = school;
        this.indexes = new ArrayList<>();
        this.AU = AU;
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

    public int getAU() {
        return AU;
    }

    public void setAU(int AU) {
        this.AU = AU;
    }
}