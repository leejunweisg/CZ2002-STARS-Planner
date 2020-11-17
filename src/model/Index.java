package model;

import java.util.ArrayList;

public class Index {
    private Course course;
    private int index_number;
    private int max_capacity;

    private ArrayList<Student> enrolledStudents;
    private ArrayList<Student> waitlistedStudents;

    private ArrayList<TimeSlot> lectures;
    private ArrayList<TimeSlot> tutorials;
    private ArrayList<TimeSlot> labs;

    public Index(Course course, int index_number, int max_capacity) {
        this.course = course;
        this.index_number = index_number;
        this.max_capacity = max_capacity;

        enrolledStudents = new ArrayList<>();
        waitlistedStudents = new ArrayList<>();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getIndex_number() {
        return index_number;
    }

    public void setIndex_number(int index_number) {
        this.index_number = index_number;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public ArrayList<Student> getWaitlistedStudents() {
        return waitlistedStudents;
    }

    public void setWaitlistedStudents(ArrayList<Student> waitlistedStudents) {
        this.waitlistedStudents = waitlistedStudents;
    }

    public ArrayList<TimeSlot> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<TimeSlot> lectures) {
        this.lectures = lectures;
    }

    public ArrayList<TimeSlot> getTutorials() {
        return tutorials;
    }

    public void setTutorials(ArrayList<TimeSlot> tutorials) {
        this.tutorials = tutorials;
    }

    public ArrayList<TimeSlot> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<TimeSlot> labs) {
        this.labs = labs;
    }
}
