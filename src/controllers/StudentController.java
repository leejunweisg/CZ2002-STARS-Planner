package controllers;

import FileManager.FileManager;
import model.*;

import java.util.ArrayList;

public class StudentController {

    private FileManager fm;
    private ArrayList<Course> courseList;
    private ArrayList<Student> studentList;

    public StudentController(){
        fm = new FileManager();
    }


    public String registerForCourse(String username, String courseCode, int indexNumber){
        courseList = fm.read_course();
        studentList = fm.read_student();

        Student stud = getStudentByUsername(username);
        Course c = getCourseByCode(courseCode);

        //TODO VALIDATION, NEED TO CHECK VACANCY, CLASH ETC
        for (Index i: c.getIndexes()){
            if (i.getIndex_number() == indexNumber) {
                i.getEnrolledStudents().add(stud);
                stud.getRegistered().add(i);
                fm.write_course(courseList);
                fm.write_student(studentList);
            }
        }
        return "";
    }

    private Student getStudentByUsername(String matric){
        for (Student stud: studentList)
            if (stud.getUsername().equals(matric))
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
