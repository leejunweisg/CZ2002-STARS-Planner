package Model;

import java.time.LocalDate;

public class Staff extends User{
    private String staff_number;

    /**
     *Constructor of User Staff
     * @param username Username of staff member
     * @param password Password of staff member
     * @param fullname Full name of staff member
     * @param gender Gender of staff member in Enum
     * @param nationality Nationality of staff member
     * @param dob Date of birth of staff member
     * @param staff_number Staff number of staff member
     */
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