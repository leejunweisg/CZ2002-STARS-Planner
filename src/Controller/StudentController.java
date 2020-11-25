package Controller;

import FileManager.DataContainer;
import FileManager.FileManager;
import Notification.Email;
import Notification.NotificationInterface;
import Model.Course;
import Model.Index;
import Model.Student;
import Model.TimeSlot;
import java.util.ArrayList;

/**
 * Controller to contain all the business logic for student-related functions.
 */
public class StudentController {
    private final DataContainer dc;

    /**
     * The constructor.
     * @param dc The DataContainer instance that contains the arraylists.
     */
    public StudentController(DataContainer dc) {
        this.dc = dc;
    }

    /**
     * Registers a student into an index of a particular course.
     * @param username The student's username.
     * @param courseCode The course code.
     * @param indexNumber The index number in the course.
     * @return Returns a success/error message.
     */
    public String registerForCourse(String username, String courseCode, int indexNumber){
        Student stud = dc.getStudentByUsername(username);
        Course c = dc.getCourseByCode(courseCode);

        // check if student already registered for this course
        for (Index i: stud.getRegistered())
            if (i.getCourse() == c)
                return "You are already registered for this course!";

        // check if student already waitlisted for this course
        for (Index i: stud.getWaitlisted())
            if (i.getCourse() == c)
                return "You are currently in the waitlist for this course!";

        // check AUs quota
        int current_AU=0;
        for (Index index : stud.getRegistered()) current_AU+=index.getCourse().getAU(); // sum registered AU
        for (Index index : stud.getWaitlisted()) current_AU+=index.getCourse().getAU(); // sum waitlisted AU
        if (stud.getMaximumAUs() - current_AU < c.getAU())
            return String.format("Registration unsuccessful due to insufficient AUs. You have spent %d AUs from registered and waitlisted courses.",current_AU);

        // get index
        Index i = dc.getCourseIndex(c, indexNumber);

        // check time clash for registered course
        for (Index idx: stud.getRegistered())
            if (indexClashed(i, idx))
                return "Registration unsuccessful, the index clashed with another registered course!";

        // check time clash for waitlisted course
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

    /**
     * Drop a registered course index for a student.
     * @param username The student's username.
     * @param courseCode The course code.
     * @param indexNumber The index number in the course.
     * @return Returns a success/error message.
     */
    public String dropRegisteredCourse(String username, String courseCode, int indexNumber){
        Student stud = dc.getStudentByUsername(username);
        Course c = dc.getCourseByCode(courseCode);
        Index i = dc.getCourseIndex(c, indexNumber);

        if (stud.getRegistered().contains(i)) {
            deregisterStudent(i, stud);
            FileManager.write_all(dc);
            return "You have successfully dropped the course!";

        }else{
            return "You are not registered in that index!";
        }
    }

    /**
     * Drop a waitlisted course index for a student.
     * @param username The student's username.
     * @param courseCode The course code.
     * @param indexNumber The index number in the course.
     * @return Returns a success/error message.
     */
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

    /**
     * Returns all registered/waitlisted courses for a student.
     * @param username The student's username.
     * @return Returns a string with all registered/waitlisted courses and indexes.
     */
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

    /**
     * Returns all waitlisted courses for a student.
     * @param username The student's username.
     * @return Returns a string with all waitlisted courses and indexes.
     */
    public String printWaitListedCourse(String username){
        Student stud = dc.getStudentByUsername(username);

        if ((stud.getWaitlisted().size()) <= 0)
            return null;

        StringBuilder sb = new StringBuilder();

        for (Index i: stud.getWaitlisted()) {
            sb.append(i);
            sb.append("Status: ").append("IN WAITLIST\n");
        }
        return sb.toString();

    }

    /**
     * Returns all registered courses for a student.
     * @param username The student's username.
     * @return Returns a string with all registered courses and indexes.
     */
    public String printRegisteredCourse(String username){
        Student stud = dc.getStudentByUsername(username);

        if ((stud.getRegistered().size()) <= 0)
            return null;
        StringBuilder sb = new StringBuilder();

        for (Index i: stud.getRegistered()) {
            sb.append(i);
            sb.append("Status: ").append("REGISTERED\n");
        }
        return sb.toString();

    }

    /**
     * De-register a student from a course index
     * @param i The course Index.
     * @param stud The Student.
     */
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

            front.getWaitlisted().remove(i);
            i.getWaitlistedStudents().remove(front);

            // TODO email notification
            NotificationInterface ni = new Email();
            String message = String.format("You have successfully registered for:\n%s %s\nIndex: %d", i.getCourse().getCourse_code(), i.getCourse().getCourse_name(), i.getIndex_number());
            ni.sendNotification("NTU STARS Registration Notification", message, front.getEmail());

            FileManager.write_all(dc);
        }
    }

    /**
     * Register a student to a course index
     * @param i The course Index.
     * @param stud The Student.
     */
    private void registerStudent(Index i, Student stud){
        i.getEnrolledStudents().add(stud);
        stud.getRegistered().add(i);
    }

    /**
     * Swaps the index between two students.
     * @param username1 The first student's username.
     * @param username2 The second student's username.
     * @param courseCode The course code.
     * @param oldIndexNum The first student's index number.
     * @param newIndexNum The second student's index number.
     * @return Returns a success/error message.
     */
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

    /**
     * Checks if two course Indexes contains lessons with time clash.
     * @param i1 The first course Index
     * @param i2 The second course Index
     * @return Returns true if clash is detected, else false.
     */
    private boolean indexClashed(Index i1, Index i2){
        ArrayList<TimeSlot> ts_list1 = i1.getAllSlots();
        ArrayList<TimeSlot> ts_list2 = i2.getAllSlots();

        for (TimeSlot ts1: ts_list1)
            for(TimeSlot ts2: ts_list2)
                if(timeslotClashed(ts1,ts2))
                    return true;

        return false;
    }

    /**
     * Checks if two TimeSlots have a time clash.
     * @param ts1 The first TimeSlot.
     * @param ts2 The second TimeSlot.
     * @return Returns true if clash is detected, else false.
     */
    private boolean timeslotClashed(TimeSlot ts1, TimeSlot ts2){
        return ts1.getDayOfWeek() == ts2.getDayOfWeek() && ts1.getStartTime().isBefore(ts2.getEndTime()) && ts2.getStartTime().isBefore(ts1.getEndTime());
    }

    /**
     * Prints all courses.
     */
    public void printAllCourses(){
        System.out.println("-----------All Courses-----------");
        for(Course c: dc.getCourseList()) {
            System.out.println(c);
        }
    }

    /**
     * Change the index of a registered course index for a student.
     * @param username The student's username.
     * @param courseCode The course code.
     * @param oldIndexNum The old index number.
     * @param newIndexNum The new index number.
     * @return Returns a success/error message.
     */
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
            else
                return "New index has no vacancies!";

        return "You are not registered in that index!";
    }

    /**
     * Checks if a student is registered in a course Index.
     * @param username The student's username.
     * @param courseCode The course code.
     * @param indexNumber The index number in the course.
     * @return Returns true if the student is registered in that course Index, else false.
     */
    public boolean checkRegisteredIndex(String username, String courseCode, int indexNumber){
        Student stud = dc.getStudentByUsername(username);
        for (Index i : stud.getRegistered()){
            if (i.getCourse().getCourse_code().equals(courseCode) && i.getIndex_number() == indexNumber){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param courseCode
     */
    public void printAllIndexByCourse(String courseCode){
        System.out.println("-----------Index Of Course-----------");
        Course c = dc.getCourseByCode(courseCode);
        System.out.println("Course: " + c.getCourse_code() + " " + c.getCourse_name());
        System.out.print("Index: ");
        for(int i = 0; i<c.getIndexes().size(); i++) {
            System.out.print(c.getIndexes().get(i).getIndex_number() + " ");
        }
        System.out.println(" \n");

    }


}