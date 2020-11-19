package controllers;

import FileManager.FileManager;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class StaffController {

    private FileManager fm;
    private ArrayList<Staff> staffList;
    private ArrayList<Student> studentList;
    private ArrayList<Course> courseList;

    public StaffController() {
        fm = new FileManager();
    }

    // validation methods to be used from the UI
    public boolean existUsername(String username){
        // read latest students from file
        studentList = fm.read_student();
        for (Student stud: studentList)
            if (stud.getUsername().equals(username))
                return true;
        for (Staff s: staffList)
            if (s.getUsername().equals(username))
                return true;

        return false;
    }

    public boolean existMatricNumber(String matric_number){
        // read latest students from file
        studentList = fm.read_student();
        for (Student stud: studentList)
            if (stud.getMatric_number().equals(matric_number))
                return true;

        return false;
    }

    public boolean existSchool(String school){
        try{
            School.valueOf(school);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public boolean existCourse(String courseCode){
        courseList = fm.read_course();
        for (Course c: courseList){
            if (c.getCourse_code().equals(courseCode.toUpperCase()))
                return true;
        }
        return false;
    }

    // methods for menu tasks
    public String addStudent(String username, String passwordPlain, String fullname, String gender, String nationality, String dob, String matric_number, String matriculation_date){
        // read latest students & staff data from file
        studentList = fm.read_student();
        staffList = fm.read_staff();

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

    public String setStudentAccess(String matric_number, String startPeriod,String endPeriod){
        // read latest students from file
        fm.read_student();

        // parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
        LocalDateTime start = LocalDateTime.parse(startPeriod, formatter);
        LocalDateTime end = LocalDateTime.parse(endPeriod, formatter);

        // get the student
        Student stud = getStudentByMatric(matric_number);

        // if not null, set the period, if not, return error message
        if (stud!=null){
            // set period
            stud.setStartPeriod(start);
            stud.setEndPeriod(end);

            // write changes to file
            fm.write_student(studentList);

            // return success message
            return "Access period successfully set!";

        }else
            return "Not successful! The student was not found.";
    }

    public String createCourse(String courseCode, String courseName, String courseSchool){
        courseList = fm.read_course();

        School school = School.valueOf(courseSchool);
        courseList.add(new Course(courseCode, courseName, school));

        fm.write_course(courseList);

        return "Course Added Successfully!";

    }

    public String createIndex(String courseCode, String indexNum, String maxCap) {
        //indexList = fm.read_index();
        courseList =fm.read_course();
        Course course = getCourseByCode(courseCode);

        int indexNumber = Integer.parseInt(indexNum);
        int maxCapacity = Integer.parseInt(maxCap);
        if(course!=null) {
            ArrayList<Index> indexList = course.getIndexes(); // gives current array list of indexes
            indexList.add(new Index(course, indexNumber, maxCapacity));
            course.setIndexes(indexList);
            fm.write_course(courseList);
            return "Success!";
        }
        else{
            return "failed";
        }
    }

    public String checkIndexSlot(String courseCode, int indexNumber){
        // read latest courses from file
        courseList = fm.read_course();

        // retrieve the course
        Course c = getCourseByCode(courseCode);
        ArrayList<Index> indexes = c.getIndexes();

        // if course does not have any index
        if (indexes == null)
            return "This course has no indexes!";

        // find the index
        for (Index i: c.getIndexes()) {
            if (i.getIndex_number() == indexNumber)
                return String.format("\nCourse: %s %s \t Index: %d \t Capacity: %d/%d", courseCode, c.getCourse_name(),
                        indexNumber, i.getEnrolledStudents().size(), i.getMax_capacity());
        }

        // index not found
        return "The index number you entered was not found in the course.";
    }

    public String printByIndex(String courseCode, int indexNumber){
        return "";
    }

    // internal class helper methods
    private Student getStudentByMatric(String matric){
        for (Student stud: studentList)
            if (stud.getMatric_number().equals(matric))
                return stud;
        return null;
    }

    private Student getStudentByUsername(String username){
        for (Student stud: studentList)
            if (stud.getUsername().equals(username))
                return stud;
        return null;
    }

    private Course getCourseByCode(String courseCode){
        for(Course course: courseList)
        {
            if(course.getCourse_code().equals(courseCode))
                return course;
        }
        return null;
    }

}