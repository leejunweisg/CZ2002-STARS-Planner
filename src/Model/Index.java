package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an index belonging to a single course. Students enrolled in a course will be divided up into the course's various indexes.
 */
public class Index implements Serializable {
    //Declaration

    /**
     * The course that this index belongs to.
     */
    private Course course;

    /**
     * The index number of this index.
     */
    private int index_number;

    /**
     * The maximum number of students that can enrol in this index.
     */
    private int max_capacity;

    /**
     * A list of students that are currently enrolled in this index.
     */
    private ArrayList<Student> enrolledStudents;

    /**
     * A list of students that are currently waitlisted for this index.
     */
    private ArrayList<Student> waitlistedStudents;

    /**
     * Represents the lessons that are conducted by this index, with each lesson having a {@link LessonType} and a {@link TimeSlot}.
     * <p>
     * Stored in a hashmap data structure that contains the LessonTypes as its keys, with each LessonType key pointing to a list of TimeSlots of that particular LessonType.
     */
    private HashMap<LessonType, ArrayList<TimeSlot>> lessons;

    /**
     * Constructor for Index.
     * Each Index is linked to only 1 Course.
     * @param course The Course object that this Index belongs to.
     * @param index_number This Index's index number.
     * @param max_capacity This Index's maximum number of students that it can take in.
     */
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

    /**
     * Gets the Course that this Index belongs to.
     * @return This Index's course.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Changes the Course that this Index belongs to.
     * @param course This Index's new course.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets the index number of this Index.
     * @return This Index's index number.
     */
    public int getIndex_number() {
        return index_number;
    }

    /**
     * Changes the index number of this Index.
     * @param index_number This Index's new index number.
     */
    public void setIndex_number(int index_number) {
        this.index_number = index_number;
    }

    /**
     * Gets the maximum number of Students that can enrol in this Index.
     * @return This Index's maximum capacity.
     */
    public int getMax_capacity() {
        return max_capacity;
    }

    /**
     * Changes the maximum number of Students that can enrol in this Index.
     * @param max_capacity This Index's new maximum capacity.
     */
    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    /**
     * Gets the list of Students that are currently enrolled in this Index.
     * @return This Index's list of currently enrolled Students.
     */
    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * Changes the list of Students that are currently enrolled in this Index.
     * @param enrolledStudents This Index's new list of enrolled Students.
     */
    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    /**
     * Gets the list of students that are waitlisted for this Index.
     * @return This Index's list of waitlisted Students.
     */
    public ArrayList<Student> getWaitlistedStudents() {
        return waitlistedStudents;
    }

    /**
     * Changes the list of Students that are waitlisted for this Index.
     * @param waitlistedStudents This Index's new list of waitlisted Students.
     */
    public void setWaitlistedStudents(ArrayList<Student> waitlistedStudents) {
        this.waitlistedStudents = waitlistedStudents;
    }

    /**
     * Changes the maximum number of Students that can enrol in this Index.
     * @param max The amount to raise the current maximum capacity by.
     */
    public void setMaxCapacity(int max){
        max_capacity = max_capacity + (max);
    }

    /**
     * Gets the list of all {@link TimeSlot}s of this Index.
     * @return This Index's list of all TimeSlots.
     */
    public ArrayList<TimeSlot> getAllSlots(){
        ArrayList<TimeSlot> all = new ArrayList<>();
        all.addAll(lessons.get(LessonType.LEC));
        all.addAll(lessons.get(LessonType.TUT));
        all.addAll(lessons.get(LessonType.LAB));
        return all;
    }

    /**
     * Gets the hashmap containing the lessons conducted by this Index.
     * @return This Index's hashmap of {@link lessons}.
     */
    public HashMap<LessonType, ArrayList<TimeSlot>> getLessons() {
        return lessons;
    }

    /**
     * Changes the lessons conducted by this Index.
     * @param lessons This Index's new hashmap of {@link #lessons}.
     */
    public void setLessons(HashMap<LessonType, ArrayList<TimeSlot>> lessons) {
        this.lessons = lessons;
    }

    /**
     * Returns the formatted key attribute information of this Index in a string type.
     * The format is as follows:
     * <p>
     * "Course: {@link Course#getCourse_code()} {@link Course#getCourse_name()}
     * <p>
     *  Index: {@link #index_number}"
     *
     */
    @Override
    public String toString() {
        return "Course: " + course.getCourse_code() + " " + course.getCourse_name() + "\n" +
                "Index: " + index_number + "\n";
    }

    /**
     * Gets the number of vacancies for this Index.
     * @return The number of additional students that can be enrolled in this Index before hitting the {@link #max_capacity}.
     */
    public int getVacancies(){
        return max_capacity - enrolledStudents.size();
    }

    /**
     * Gets the list of Lecture {@link TimeSlot}s for this Index.
     * @return This Index's list of Lecture TimeSlots.
     */
    public ArrayList<TimeSlot> getLecSlots(){
        return new ArrayList<>(lessons.get(LessonType.LEC));
    }

    /**
     * Gets the list of Tutorial {@link TimeSlot}s for this Index.
     * @return This Index's list of Tutorial TimeSlots.
     */
    public ArrayList<TimeSlot> getTutSlots(){
        return new ArrayList<>(lessons.get(LessonType.TUT));
    }

    /**
     * Gets the list of Lab {@link TimeSlot}s for this Index.
     * @return This Index's list of Lab TimeSlots.
     */
    public ArrayList<TimeSlot> getLabSlots(){
        return new ArrayList<>(lessons.get(LessonType.LAB));
    }

}