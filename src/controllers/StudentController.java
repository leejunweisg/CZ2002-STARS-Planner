package controllers;

import FileManager.FileManager;
import model.*;
import FileManager.DataContainer;

import java.util.ArrayList;

public class StudentController {
    private final DataContainer dc;

    public StudentController(DataContainer dc) {
        this.dc = dc;
    }

    public String registerForCourse(String username, String courseCode, int indexNumber){
        Student stud = dc.getStudentByUsername(username);
        Course c = dc.getCourseByCode(courseCode);

        // Check if student already registered for this course
        for (Index i: stud.getRegistered())
            if (i.getCourse() == c)
                return "You are already registered for this course!";

        // Check if student already waitlisted for this course
        for (Index i: stud.getWaitlisted())
            if (i.getCourse() == c)
                return "You are currently in the waitlist for this course!";

        //TODO Check for clash before registering/waitlisting
        for (Index i: c.getIndexes()){
            // find the index
            if (i.getIndex_number() == indexNumber) {
                // have vacancy, register
                if (i.getVacancies()> 0) {
                    i.getEnrolledStudents().add(stud);
                    stud.getRegistered().add(i);
                    FileManager.write_all(dc);
                    return "Registration successful!";
                // no vacancy, waitlist
                }else{
                    i.getWaitlistedStudents().add(stud);
                    stud.getWaitlisted().add(i);
                    FileManager.write_all(dc);
                    return "The selected index is currently full! You have been added to the waitlist.";
                }
            }
        }
        return "The index number you entered was not found in the course.";
    }

    public String dropRegisteredCourse(String username, String courseCode, int indexNumber){
        Student stud = dc.getStudentByUsername(username);
        Course c = dc.getCourseByCode(courseCode);
        Index i = dc.getCourseIndex(c, indexNumber);

        // remove index from student
        // remove student from index
        if (stud.getRegistered().contains(i)) {
            stud.getRegistered().remove(i);
            i.getEnrolledStudents().remove(stud);

            // write to file
            FileManager.write_all(dc);
            return "You have successfully dropped the course!";

        }else{
            return "You are not registered in that index!";
        }
    }

    public String dropWaitlistedCourse(String username, String courseCode, int indexNumber){
        Student stud = dc.getStudentByUsername(username);
        Course c = dc.getCourseByCode(courseCode);
        Index i = dc.getCourseIndex(c, indexNumber);

        // remove index from student
        // remove student from index
        if (stud.getWaitlisted().contains(i)) {
            stud.getWaitlisted().remove(i);
            i.getWaitlistedStudents().remove(stud);

            // write to file
            FileManager.write_all(dc);
            return "You have been removed from the waitlist!";

        }else{
            return "You are not in the waitlist for that index!";
        }
    }

    public String displayStudentCourse(String username){
        Student stud = dc.getStudentByUsername(username);

        // return error message if user is not registered for anything
        if (stud.getRegistered().size() + stud.getWaitlisted().size() <= 0)
            return "You are not registered for any courses!";

        // build string of registred/waitlisted courses
        StringBuilder sb = new StringBuilder();
        for (Index i: stud.getRegistered())
            sb.append(i).append("\n");

        return sb.toString();
    }
}

