package controllers;

import FileManager.DataContainer;
import model.Staff;
import model.Student;

import java.util.ArrayList;

public class LoginController {
    private DataContainer dc;

    public LoginController(DataContainer dc){
        this.dc = dc;
    }

    public int authenticate(String username, String password){
        // load latest student & staff from file

        for (Staff s : dc.getStaffList())
            if (s.getUsername().equals(username) && s.getPasswordHash().equals(password))
                return 1;

        for (Student stud : dc.getStudentList())
            if (stud.getUsername().equals(username) && stud.getPasswordHash().equals(password))
                return 2;

        // no username/password pair found
        return 0;
    }

}
