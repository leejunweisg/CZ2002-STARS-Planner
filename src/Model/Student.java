package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public String getMatric_number() {
        return matric_number;
    }

    public void setMatric_number(String matric_number) {
        this.matric_number = matric_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getMatriculation_date() {
        return matriculation_date;
    }

    public void setMatriculation_date(LocalDate matriculation_date) {
        this.matriculation_date = matriculation_date;
    }

    public LocalDateTime getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDateTime startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDateTime getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDateTime endPeriod) {
        this.endPeriod = endPeriod;
    }

    public ArrayList<Index> getRegistered() {
        return registered;
    }

    public void setRegistered(ArrayList<Index> registered) {
        this.registered = registered;
    }

    public ArrayList<Index> getWaitlisted() {
        return waitlisted;
    }

    public void setWaitlisted(ArrayList<Index> waitlisted) {
        this.waitlisted = waitlisted;
    }

    public int getMaximumAUs() {
        return maximumAUs;
    }

    public void setMaximumAUs(int maximumAUs) {
        this.maximumAUs = maximumAUs;
    }

    @Override
    public String toString() {
        return String.format("%9s \t %15s \t %1s %10s", matric_number, getFullname(), getGender(), getNationality());
    }
}