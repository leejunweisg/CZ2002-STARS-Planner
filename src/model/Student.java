package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Student extends User{
    private String matric_number;
    private LocalDate matriculation_date;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;

    private ArrayList<Index> registered;
    private ArrayList<Index> waitlisted;

    public Student(String username, String passwordPlain, String fullname, Gender gender, String nationality, LocalDate dob, String matric_number, LocalDate matriculation_date) {
        super(username, passwordPlain, fullname, gender, nationality, dob);
        this.matric_number = matric_number;
        this.matriculation_date = matriculation_date;

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

    @Override
    public String toString() {
        return String.format("%9s \t %15s \t %1s %10s", matric_number, getFullname(), getGender(), getNationality());
    }
}