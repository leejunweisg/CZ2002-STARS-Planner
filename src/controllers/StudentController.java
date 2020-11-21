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

        Index i = dc.getCourseIndex(c, indexNumber);

        // time clash for registered course
        for (Index idx: stud.getRegistered())
            if (indexClashed(i, idx))
                return "Registration unsuccessful, the index clashed with another registered course!";

        // time clash for waitlisted course
        for (Index idx: stud.getWaitlisted())
            if (indexClashed(i, idx))
                return "Registration unsuccessful, the index clashed with another course in your waitlist!";

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
        if ((stud.getRegistered().size() + stud.getWaitlisted().size()) <= 0)
            return "You are not registered for any course!";

        // build string of registered/waitlisted courses
        StringBuilder sb = new StringBuilder();
        for (Index i: stud.getRegistered()) {
            sb.append(i);
            sb.append("Status: ").append("REGISTERED\n");
        }

        for (Index i: stud.getWaitlisted()) {
            sb.append(i);
            sb.append("Status: ").append("IN WAITLIST\n");
        }

        return sb.toString();
    }

    public String changeIndex(String username, String courseCode, int oldIndexNum, int newIndexNum) {
        Student stud = dc.getStudentByUsername(username);
        Course c = dc.getCourseByCode(courseCode);
        Index oldIndex = dc.getCourseIndex(c, oldIndexNum);
        Index newIndex = dc.getCourseIndex(c, newIndexNum);

        // if student is indeed registered in the old index
        if (stud.getRegistered().contains(oldIndex))
            if (newIndex.getVacancies() > 0){
                deregisterStudent(oldIndex, stud);
                registerStudent(newIndex, stud);
                FileManager.write_all(dc);
                return "Index successfully changed!";
            }

        return "You are not registered in that index!";
    }

    private void deregisterStudent(Index i, Student stud){
        // remove student from index
        i.getEnrolledStudents().remove(stud);

        // remove index from student
        stud.getRegistered().remove(i);

        // if exist, enrol one student from waitlist and send notification
        if (i.getVacancies()>0 && i.getWaitlistedStudents().size()>0){
            Student front = i.getWaitlistedStudents().get(0);
            i.getEnrolledStudents().add(front);
            front.getRegistered().add(i);

            // TODO notification
            System.out.printf("Notification to Matric Number %s: You have been enrolled into %s %s, %d",
                    front.getMatric_number(), i.getCourse().getCourse_code(), i.getCourse().getCourse_name(),
                    i.getIndex_number());

            FileManager.write_all(dc);
        }
    }

    private void registerStudent(Index i, Student stud){
        i.getEnrolledStudents().add(stud);
        stud.getRegistered().add(i);
    }

    public String swapIndex(String username1, String username2, String courseCode, int oldIndexNum, int newIndexNum){
        Student stud1 = dc.getStudentByUsername(username1);
        Student stud2 = dc.getStudentByUsername(username2);

        Course c = dc.getCourseByCode(courseCode);
        Index oldIndex = dc.getCourseIndex(c, oldIndexNum);
        Index newIndex = dc.getCourseIndex(c, newIndexNum);

        // check if first student is registered for oldIndex
        if (!stud1.getRegistered().contains(oldIndex))
            return "You are not registered in that index!";

        // check if second student is registered for newIndex
        if (!stud2.getRegistered().contains(newIndex))
            return "The second student is not registered in that index!";

        // check student 1
        for (Index idx: stud1.getRegistered())
            if (idx!=oldIndex && indexClashed(newIndex, idx))
                return "Registration unsuccessful, the index clashed with another registered course!";
        for (Index idx: stud1.getWaitlisted())
            if (indexClashed(newIndex, idx))
                return "Registration unsuccessful, the index clashed with another course in your waitlist!";

        // check student 2
        for (Index idx: stud2.getRegistered())
            if (idx!= newIndex && indexClashed(oldIndex, idx))
                return "Registration unsuccessful, the index clashed with the second student's registered course!";
        for (Index idx: stud2.getWaitlisted())
            if (indexClashed(oldIndex, idx))
                return "Registration unsuccessful, the index clashed with another course in the second student's waitlist!";

        // perform swap
        registerStudent(newIndex, stud1);
        registerStudent(oldIndex, stud2);
        deregisterStudent(newIndex, stud2);
        deregisterStudent(oldIndex, stud1);

        FileManager.write_all(dc);
        return "Index successfully swapped!";
    }

    private boolean indexClashed(Index i1, Index i2){
        ArrayList<TimeSlot> ts_list1 = i1.getAllSlots();
        ArrayList<TimeSlot> ts_list2 = i2.getAllSlots();

        for (TimeSlot ts1: ts_list1)
            for(TimeSlot ts2: ts_list2)
                if(timeslotClashed(ts1,ts2))
                    return true;

        return false;
    }

    private boolean timeslotClashed(TimeSlot ts1, TimeSlot ts2){
        return ts1.getDayOfWeek() == ts2.getDayOfWeek() && ts1.getStartTime().isBefore(ts2.getEndTime()) && ts2.getStartTime().isBefore(ts1.getEndTime());
    }

}