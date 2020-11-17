package controllers;

import FileManager.FileManager;
import model.Course;
import model.Student;

import java.util.ArrayList;

public class StudentController {

    private FileManager fm;
    private ArrayList<Course> courseList;
    private ArrayList<Student> studentList;

    public StudentController(){
        fm = new FileManager();
    }

}
