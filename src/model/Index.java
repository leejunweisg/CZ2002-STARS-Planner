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

    private HashMap<LessonType, ArrayList<TimeSlot>> lessons;

    public Index(Course course, int index_number, int max_capacity) {
        this.course = course;
        this.index_number = index_number;
        this.max_capacity = max_capacity;

        enrolledStudents = new ArrayList<>();
        waitlistedStudents = new ArrayList<>();

        lessons = new HashMap<>();
        lessons.put(LessonType.LEC, new ArrayList<TimeSlot>());
        lessons.put(LessonType.TUT, new ArrayList<TimeSlot>());
        lessons.put(LessonType.LAB, new ArrayList<TimeSlot>());
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

    public ArrayList<TimeSlot> getAllSlots(){
        ArrayList<TimeSlot> all = new ArrayList<>();
        all.addAll(lessons.get(LessonType.LEC));
        all.addAll(lessons.get(LessonType.TUT));
        all.addAll(lessons.get(LessonType.LAB));
        return all;
    }

    public HashMap<LessonType, ArrayList<TimeSlot>> getLessons() {
        return lessons;
    }

    public void setLessons(HashMap<LessonType, ArrayList<TimeSlot>> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "Course: " + course.getCourse_code() + " " + course.getCourse_name() + "\n" +
                "Index: " + index_number + "\n";
    }

    public int getVacancies(){
        return max_capacity - enrolledStudents.size();
    }

    public ArrayList<TimeSlot> getLecSlots(){
        return new ArrayList<>(lessons.get(LessonType.LEC));
    }

    public ArrayList<TimeSlot> getTutSlots(){
        return new ArrayList<>(lessons.get(LessonType.TUT));
    }

    public ArrayList<TimeSlot> getLabSlots(){
        return new ArrayList<>(lessons.get(LessonType.LAB));
    }

}
