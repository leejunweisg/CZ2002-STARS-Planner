package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Abstract class that represents a User account in the STARSPlanner system.
 */
public abstract class User implements Serializable {
    /**
     * Username of the user
     */
    private String username;

    /**
     * Password hash of the user
     */
    private byte[] passwordHash;

    /**
     * Full name of the user
     */
    private String fullname;

    /**
     * Gender of the user
     */
    private Gender gender;

    /**
     * Nationality of the user
     */
    private String nationality;

    /**
     * Date of birth of the user
     */
    private LocalDate dob;

    /**
     * Constructor for User
     * @param username Login username e.g (WTAN0001)
     * @param password Login password
     * @param fullname Full name of user
     * @param gender Gender of user, Male/Female (M/F) selected from enumerator Gender
     * @param nationality Nationality of user e.g(Singaporean)
     * @param dob Date of birth of user
     */
    public User(String username, byte[] password, String fullname, Gender gender, String nationality, LocalDate dob) {
        this.username = username;
        this.passwordHash = password;
        this.fullname = fullname;
        this.gender = gender;
        this.nationality = nationality;
        this.dob = dob;
    }

    /**
     * Get the username.
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password hash.
     * @return The password hash of the user.
     */
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    /**
     * Set the password hash.
     * @param passwordHash The new password hash.
     */
    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Get the full name.
     * @return The full name of the user.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set the full name.
     * @param fullname The new full name.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Get the gender.
     * @return The gender of the user.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set the gender.
     * @param gender The new gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Get the nationality.
     * @return The nationality of the user.
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Set the nationality.
     * @param nationality The new nationality.
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Get the date of birth.
     * @return The date of birth of the user.
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Set the date of birth.
     * @param dob The new date of birth.
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Get a string representation of the user.
     * @return Returns a string representation of the user.
     */
    @Override
    public String toString() {
        return String.format("Name: %s (%s) Nationality: %s", fullname, gender.toString(), nationality);
    }


}