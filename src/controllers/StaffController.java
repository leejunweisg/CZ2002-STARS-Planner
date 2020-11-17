package controllers;

import FileManager.FileManager;
import model.Course;
import model.Gender;
import model.Staff;
import model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StaffController {

    private FileManager fm;
    private ArrayList<Staff> staffList;
    private ArrayList<Student> studentList;
    private ArrayList<Course> courseList;

    public StaffController() {
        fm = new FileManager();
    }

    public String addStudent(String username, String passwordPlain, String fullname, String gender, String nationality, String dob, String matric_number, String matriculation_date){
        // read latest students & staff data from file
        studentList = fm.read_student();
        staffList = fm.read_staff();

        // check if username exists
        if (existUsername(username))
            return "Student was not added, username already exists!";

        // check if matric_number exists
        if (existMatricNumber(matric_number))
            return "Student was not added, matriculation number already exists!";

        // convert variables to required types
        Gender genderEnum = Gender.valueOf(gender);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dobParsed = LocalDate.parse(dob, formatter);
        LocalDate matricDateParsed = LocalDate.parse(matriculation_date, formatter);

        // add student
        studentList.add(new Student(username, passwordPlain, fullname, genderEnum, nationality, dobParsed, matric_number, matricDateParsed));

        // write changes to file
        fm.write_student(studentList);

        return "Student added successfully!";
    }

    private boolean existUsername(String username){
        for (Student stud: studentList)
            if (stud.getUsername().equals(username))
                return true;
        for (Staff s: staffList)
            if (s.getUsername().equals(username))
                return true;

        return false;
    }

    private boolean existMatricNumber(String matric_number){
        for (Student stud: studentList)
            if (stud.getMatric_number().equals(matric_number))
                return true;

        return false;
    }
}
