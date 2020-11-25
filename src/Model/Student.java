package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Abstract class that represents a Student account in the STARSPlanner system.
 */
public class Student extends User{
    private String matric_number;
    private String email;
    private LocalDate matriculation_date;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
    private int maximumAUs;
    private ArrayList<Index> registered;
    private ArrayList<Index> waitlisted;

    /**
     * Constructor for User Student
     * @param username Username of student
     * @param password Password of student
     * @param fullname Full Name of student
     * @param gender Gender of student in Enum
     * @param nationality Nationality of student
     * @param dob Date of birth of student
     * @param matric_number Matriculation number of student
     * @param matriculation_date Matriculation date of student
     * @param email Email address of student
     */
    public Student(String username, byte[] password, String fullname, Gender gender, String nationality, LocalDate dob, String matric_number, LocalDate matriculation_date, String email) {
        super(username, password, fullname, gender, nationality, dob);
        this.matric_number = matric_number;
        this.matriculation_date = matriculation_date;
        this.email = email;
        this.maximumAUs = 21;

        // set default start and end period to current time
        this.startPeriod = LocalDateTime.now();
        this.endPeriod = LocalDateTime.now();

        // initialize registered list
        registered = new ArrayList<>();
        waitlisted = new ArrayList<>();
    }

    /**
     * Get the matriculation number of the student
     * @return The matriculation number of the student.
     */
    public String getMatric_number() {
        return matric_number;
    }

    /**
     * Set the matriculation number of the student
     * @param matric_number The new matriculation number.
     */
    public void setMatric_number(String matric_number) {
        this.matric_number = matric_number;
    }

    /**
     * Get the email of the student
     * @return The email of the student.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the student
     * @param email The new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the matriculation date of the student
     * @return The matriculation date of the student.
     */
    public LocalDate getMatriculation_date() {
        return matriculation_date;
    }

    /**
     * Set the matriculation date of the student
     * @param matriculation_date The new matriculaton date
     */
    public void setMatriculation_date(LocalDate matriculation_date) {
        this.matriculation_date = matriculation_date;
    }

    /**
     * Get the start period date/time of the student
     * @return The start period date/time of the student.
     */
    public LocalDateTime getStartPeriod() {
        return startPeriod;
    }

    /**
     * Set the start period date/time of the student
     * @param startPeriod The new start period date/time
     */
    public void setStartPeriod(LocalDateTime startPeriod) {
        this.startPeriod = startPeriod;
    }

    /**
     * Get the end period date/time of the student
     * @return The end period date/time of the student.
     */
    public LocalDateTime getEndPeriod() {
        return endPeriod;
    }

    /**
     * Set the end period date/time of the student
     * @param endPeriod The new end period date/time
     */
    public void setEndPeriod(LocalDateTime endPeriod) {
        this.endPeriod = endPeriod;
    }

    /**
     * Get an arraylist of registered course Indexes of the student
     * @return The arraylist of registered course Indexes of the student
     */
    public ArrayList<Index> getRegistered() {
        return registered;
    }

    /**
     * Set the arraylist of registered course Indexes of the student
     * @param registered The new arraylist of registered course Indexes
     */
    public void setRegistered(ArrayList<Index> registered) {
        this.registered = registered;
    }

    /**
     * Get an arraylist of waitlisted course Indexes of the student
     * @return The arraylist of waitlisted course Indexes of the student
     */
    public ArrayList<Index> getWaitlisted() {
        return waitlisted;
    }

    /**
     * Set the arraylist of waitlisted course Indexes of the student
     * @param waitlisted The new arraylist of waitlisted course Indexes
     */
    public void setWaitlisted(ArrayList<Index> waitlisted) {
        this.waitlisted = waitlisted;
    }

    /**
     * Get the maximum AUs limit of the student
     * @return The maximum AUs limit of the student
     */
    public int getMaximumAUs() {
        return maximumAUs;
    }

    /**
     * Set the maximum AUs limit of the student
     * @param maximumAUs The new maximum AU limit.
     */
    public void setMaximumAUs(int maximumAUs) {
        this.maximumAUs = maximumAUs;
    }

    /**
     * Get a string representation of the student.
     * @return Returns a string representation of the student.
     */
    @Override
    public String toString() {
        return String.format("%9s \t %15s \t %1s %10s", matric_number, getFullname(), getGender(), getNationality());
    }
}