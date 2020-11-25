package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a course provided by the school for students to enrol in.
 */
public class Course implements Serializable {

    /**
     * The course code that uniquely identifies this course.
     * Must be unique (i.e. no other Course has the same course code as this Course).
     * Must follow the format AB1234: where A and B can be any alphabet (lower case will be converted to uppercase) and 1, 2, 3 and 4 can be any integers 0-9.
     */
    private String course_code;

    /**
     * The name of this course.
     */
    private String course_name;

    /**
     * The school that administers this course.
     * The value of this attribute has to be an element of the {@link School} enumeration, which is currently NBS and SCSE.
     */
    private School school;

    /**
     * The number of Academic Units that is assigned to this course.
     */
    private int AU;

    // holds a list of indexes that this course has

    /**
     * A list of indexes that belongs to this course.
     */
    private ArrayList<Index> indexes;

    //New Constructor

    /**
     * Constructor for Courses.
     * @param course_code This Course's course code.
     * @param course_name This Course's course name.
     * @param school The school which administers this Course.
     * @param AU This Course's number of academic units.
     */
    public Course(String course_code, String course_name, School school, int AU) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.school = school;
        this.indexes = new ArrayList<>();
        this.AU = AU;
    }

    /**
     * Gets the course code of this Course.
     * @return This Course's course code.
     */
    public String getCourse_code() {
        return course_code;
    }

    /**
     * Changes the course code of this Course.
     * New course code must be unique (i.e. no other Course has this course code).
     * @param course_code This Course's new course code.
     */
    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    /**
     * Gets the name of this Course.
     * @return This Course's name.
     */
    public String getCourse_name() {
        return course_name;
    }

    /**
     * Changes the name of this Course.
     * @param course_name This Course's new name.
     */
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    /**
     * Gets the school that administers this Course.
     * @return The school that administers this Course.
     */
    public School getSchool() {
        return school;
    }

    /**
     * Changes the school that administers this Course.
     * @param school The new school that administers this Course.
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * Gets the list of indexes that belong to this Course.
     * @return This Course's list of indexes.
     */
    public ArrayList<Index> getIndexes() {
        return indexes;
    }

    /**
     * Change the list of indexes that belong to this Course.
     * @param indexes This Course's new list of indexes.
     */
    public void setIndexes(ArrayList<Index> indexes) {
        this.indexes = indexes;
    }

    /**
     * Gets the number of Academic Units that is assigned to this Course.
     * @return This Course's number of Academic Units.
     */
    public int getAU() {
        return AU;
    }

    /**
     * Changes the number of Academic Units assigned to this Course.
     * @param AU This Course's new number of Academic Units.
     */
    public void setAU(int AU) {
        this.AU = AU;
    }
}