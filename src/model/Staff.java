package model;

import java.time.LocalDate;

public class Staff extends User{
    private String staff_number;

    public Staff(String username, byte[] password, String fullname, Gender gender, String nationality, LocalDate dob, String staff_number) {
        super(username, password, fullname, gender, nationality, dob);
        this.staff_number = staff_number;
    }

    public String getStaff_number() {
        return staff_number;
    }

    public void setStaff_number(String staff_number) {
        this.staff_number = staff_number;
    }
}