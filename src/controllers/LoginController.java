package controllers;

import FileManager.FileManager;
import model.Staff;
import model.Student;

import java.util.ArrayList;

public class LoginController {

    private FileManager fm;
    private ArrayList<Student> studentList;
    private ArrayList<Staff> staffList;

    public LoginController(){
        fm = new FileManager();
    }

    public int authenticate(String username, String password){
        // load latest student & staff from file
        studentList = fm.read_student();
        staffList = fm.read_staff();

        for (Staff s : staffList)
            if (s.getUsername().equals(username) && s.getPasswordHash().equals(password))
                return 1;

        for (Student stud : studentList)
            if (stud.getUsername().equals(username) && stud.getPasswordHash().equals(password))
                return 2;

        // no username/password pair found
        return 0;
    }

}
