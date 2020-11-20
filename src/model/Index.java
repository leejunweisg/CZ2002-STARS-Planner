package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Index implements Serializable {
    //Declaration
    private Course course;
    private int index_number;
    private int max_capacity;

    private ArrayList<Student> enrolledStudents;
    private ArrayList<Student> waitlistedStudents;


    //private HashMap<sessionType, ArrayList<TimeSlot>> sessions;

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

    public void setMaxCapacity(int max){
        max_capacity = max_capacity + (max);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course: ").append(course.getCourse_code()).append(" ").append(course.getCourse_name()).append("\n");
        sb.append("Index: ").append(index_number).append("\n");
        sb.append("Status: ").append("REGISTERED");
        return sb.toString();
    }

    // simple helper functions (not considered as business logic)
    public int getVacancies(){
        return max_capacity - enrolledStudents.size();
    }



}
