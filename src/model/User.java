package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private String username;
    private byte[] passwordHash;
    private String fullname;
    private Gender gender;
    private String nationality;
    private LocalDate dob;

    public User(String username, byte[] password, String fullname, Gender gender, String nationality, LocalDate dob) {
        this.username = username;
        this.passwordHash = password;
        this.fullname = fullname;
        this.gender = gender;
        this.nationality = nationality;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return String.format("Name: %s (%s) Nationality: %s", fullname, gender.toString(), nationality);
    }


}