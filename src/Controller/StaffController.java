package controller;

import filemanager.DataContainer;
import filemanager.FileManager;
import model.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Staff Controller: Control of staff processes in STARSPlanner
 */
public class StaffController {

    /**
     * The DataContainer object that contains the arraylists.
     */
    private final DataContainer dc;

    /**
     * The constructor
     * @param dc The DataContainer object that contains the arraylists.
     */
    public StaffController(DataContainer dc) {
        this.dc = dc;
    }

    // methods for menu tasks

    /**
     * Method for Adding new student
     * @param username Username of student
     * @param passwordPlain Password of student
     * @param fullname Full Name of student
     * @param gender Gender of student
     * @param nationality Nationiality of student
     * @param dob Date of birth of student
     * @param matric_number Matriculation number of student
     * @param matriculation_date Matriculation date of student
     * @param email Email of student. email must be a valid, registered email
     * @return Returns print message if student is added successfully
     */
    public String addStudent(String username, String passwordPlain, String fullname, String gender, String nationality, String dob, String matric_number, String matriculation_date, String email){
        // convert variables to required types
        Gender genderEnum = Gender.valueOf(gender);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dobParsed = LocalDate.parse(dob, formatter);
        LocalDate matricDateParsed = LocalDate.parse(matriculation_date, formatter);

        // add student
        dc.getStudentList().add(new Student(username, hash(passwordPlain), fullname, genderEnum, nationality, dobParsed, matric_number, matricDateParsed, email));

        // write changes to file
        FileManager.write_all(dc);
        return "Student added successfully!";
    }

    /**
     * Method for setting student access period
     * @param matric_number Matriculation number of student
     * @param startPeriod Start period where student is able to access STARS. Consist of both time and date.
     * @param endPeriod End period where student will not be able to access STARS. Consist of both time and date.
     * @return Returns a success/error message.
     */
    public String setStudentAccess(String matric_number, String startPeriod,String endPeriod){
        // parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:m");

        LocalDateTime start = LocalDateTime.parse(startPeriod, formatter);
        LocalDateTime end = LocalDateTime.parse(endPeriod, formatter);

        // get the student
        Student stud = dc.getStudentByMatric(matric_number);

        // if not null, set the period, if not, return error message
        if (stud!=null){
            // set period
            stud.setStartPeriod(start);
            stud.setEndPeriod(end);

            // write changes to file
            FileManager.write_all(dc);

            // return success message
            return "Access period successfully set!";

        }else
            return "Not successful! The student was not found.";
    }

    /**
     * Method for creating a new course
     * @param courseCode Course Code of the new course
     * @param courseName Course Name of the new course
     * @param courseSchool School of where the course is from.
     * @param AU Number of Allocated Unit for the course
     * @return Returns a success message.
     */
    public String createCourse(String courseCode, String courseName, String courseSchool, int AU){
        School school = School.valueOf(courseSchool);
        dc.getCourseList().add(new Course(courseCode, courseName, school, AU));

        FileManager.write_all(dc);
        return "Course Added Successfully!";
    }

    /**
     * Method for changing the course code of a existing course
     * @param oldCode Old course code of the course to change
     * @param newCode New course code for the course
     * @return Returns a success message.
     */
    public String changeCourseCode(String oldCode, String newCode){
        Course c = dc.getCourseByCode(oldCode);
        c.setCourse_code(newCode);
        FileManager.write_all(dc);
        return "Course code successfully updated!";
    }

    /**
     * Method for changing the course name of a existing course
     * @param courseCode Course code of the course to change
     * @param newName New name for the course code
     * @return Returns a success message.
     */
    public String changeCourseName(String courseCode, String newName){
        Course c = dc.getCourseByCode(courseCode);
        c.setCourse_name(newName);
        FileManager.write_all(dc);
        return "Course name successfully updated!";
    }

    /**
     * Method for creating new Course Index for an existing course
     * @param courseCode Course code of course to create index
     * @param indexNum New index number for the course
     * @param maxCap Maximum capacity for the index number of the course
     * @return Returns a success message.
     */
    public String createIndex(String courseCode, int indexNum, int maxCap) {
        Course c = dc.getCourseByCode(courseCode);
        Index newIndex = new Index(c, indexNum, maxCap);

        // add index to course
        c.getIndexes().add(newIndex);

        // write changes to file
        FileManager.write_all(dc);

        return "Index added to course!";
    }

    /**
     * Method for removing index for a existing course
     * @param courseCode Course code of course to remove index
     * @param indexNum Index number of the course
     * @return Returns a success message.
     */
    public String removeIndex(String courseCode, int indexNum){
        Course c = dc.getCourseByCode(courseCode);
        Index indexToRemove = dc.getCourseIndex(c, indexNum);

        // deregister students from this index
        for (Student stud: indexToRemove.getEnrolledStudents())
            stud.getRegistered().remove(indexToRemove);
        for (Student stud: indexToRemove.getWaitlistedStudents())
            stud.getWaitlisted().remove(indexToRemove);

        // remove index from course
        c.getIndexes().remove(indexToRemove);

        // write to file
        FileManager.write_all(dc);

        return "The index has been successfully removed from the course";
    }

    /**
     * Method for adding new TimeSlot for a existing course
     * @param coursecode Course code of course to add time slot
     * @param indexNumber Index number of the course
     * @param lessonType Type of lesson. e.g(LEC, TUT, LAB)
     * @param dayOfWeek day of week in int
     * @param location Location of the lesson
     * @param startTime Starting time of the lesson.
     * @param endTime Ending time of the lesson.
     * @return Returns a success/error message.
     */
    public String addNewTimeSlot(String coursecode, int indexNumber, int lessonType, int dayOfWeek, String location, String startTime, String endTime){

        Course c = dc.getCourseByCode(coursecode);
        Index i = dc.getCourseIndex(c, indexNumber);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
        LocalTime st = LocalTime.parse(startTime, formatter);
        LocalTime et = LocalTime.parse(endTime, formatter);
        TimeSlot ts = new TimeSlot(dayOfWeek, location, st, et);

        for (TimeSlot timeslot : i.getAllSlots()){
            if (timeslotClashed(ts, timeslot))
                return "Time clashed with an existing lesson from this index!";
        }

        switch(lessonType){
            case 1-> i.getLessons().get(LessonType.LEC).add(ts);
            case 2-> i.getLessons().get(LessonType.TUT).add(ts);
            case 3-> i.getLessons().get(LessonType.LAB).add(ts);
        }

        FileManager.write_all(dc);
        return "Lesson successfully added to course!";
    }

    /**
     * Method for checking total number of slots available for a index in a existing course
     * @param courseCode Course code of course to check
     * @param indexNumber Index number of the course
     * @return Returns the number of available slots, total slots, and waitlist.
     */
    public String checkIndexSlot(String courseCode, int indexNumber){
        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        // retrieve the index from course
        Index i = dc.getCourseIndex(c, indexNumber);

        // return string
        return String.format("\nAvailable slots: %d\n" + "Total slots: %d\n" + "In waitlist: %d",
                i.getVacancies(), i.getMax_capacity(), i.getWaitlistedStudents().size());
    }

    /**
     * Method for printing list of registered students arranged by index
     * @param courseCode Course code of the course
     * @param indexNumber Index number of the course/
     * @return Returns all registered students who have enrolled in the index number. If no students are found, return print message of no students found.
     */
    public String printByIndex(String courseCode, int indexNumber) {
        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        // get the index
        Index i = dc.getCourseIndex(c, indexNumber);

        // construct string
        if (!i.getEnrolledStudents().isEmpty()) {
            StringBuilder sb;
            sb = new StringBuilder("\nAll registered students:\n");
            for (Student stud : i.getEnrolledStudents())
                sb.append(stud).append("\n");

            return sb.toString();
        }else
            return "There are no students registered for this index!";
    }

    /**
     * Method for printing list of registered students arranged by courses
     * @param courseCode Course code of the course
     * @return Return all registered students of the course. If no students are found, return print message of no student found.
     */
    public String printByCourse(String courseCode){

        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        StringBuilder sb;
        int count = 0;
        sb = new StringBuilder("\nAll registered students:\n");
        if (c != null)
            for (Index i : c.getIndexes())
                for (Student stud : i.getEnrolledStudents()) {
                    count++;
                    sb.append(String.format("%d \t %s\n", i.getIndex_number(), stud));
                }

        if (count == 0)
            return "There are no students registered for this course!";
        else
            return sb.toString();
    }

    /**
     * Method for checking if  timeslot of lesson clashes with existing time slot
     * @param ts1 time slot of the course.
     * @param ts2 TIme slot of the existing course.
     * @return Returns true if found, else false.
     */
    private boolean timeslotClashed(TimeSlot ts1, TimeSlot ts2){
        return ts1.getDayOfWeek() == ts2.getDayOfWeek() && ts1.getStartTime().isBefore(ts2.getEndTime()) && ts2.getStartTime().isBefore(ts1.getEndTime());
    }

    /**
     * Method for hashing password
     * @param passwordStr Password of user
     * @return Return a password hashed in SHA-256
     */
    private byte[] hash(String passwordStr){
        byte[] digest=null;
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            digest = sha256.digest(passwordStr.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            System.out.println();
        }
        return digest;
    }

    /**
     * Method for printing All courses and indexes of all courses in the DataContainer
     */
    public void printAllCourses(){
        System.out.println("-----------All Courses-----------");
        for(int x = 0; x<dc.getCourseList().size(); x++)
            System.out.println(dc.getCourseList().get(x).toString());
    }

    /**
     * Method for printing all time slots of a existing course in the DataContainer
     * @param courseCode Course code of the course to check
     * @param indexNumber Index number of the course.
     */
    public void printAllIndex(String courseCode, int indexNumber){
        Course c = dc.getCourseByCode(courseCode);
        Index i = dc.getCourseIndex(c, indexNumber);

        System.out.println("-----------LECTURES-----------");
        if(i.getLessons().get(LessonType.LEC).isEmpty()){
            System.out.println("No lectures found");
        }
        else {
            for (int x = 0; x < i.getLecSlots().size(); x++) {
                System.out.println(i.getLessons().get(LessonType.LEC).get(x).toString());
            }
        }
        System.out.println("-----------TUTORIALS-----------");
        if(i.getLessons().get(LessonType.TUT).isEmpty()){
            System.out.println("No Tutorial lessons found");
        }
        else {
            for (int x = 0; x < i.getTutSlots().size(); x++) {
                System.out.println(i.getLessons().get(LessonType.TUT).get(x));
            }
        }
        System.out.println("-----------LABS-----------");
        if(i.getLessons().get(LessonType.LAB).isEmpty()){
            System.out.println("No Lab lessons found");
        }
        else {
            for (int x = 0; x < i.getLabSlots().size(); x++) {
                System.out.println(i.getLessons().get(LessonType.LAB).get(x));
            }
        }
    }

    /**
     * Method for printing course code and name of existing course
     */
    public void printAddedCourses(){
        System.out.println("-----------All Courses-----------");
        for(Course c: dc.getCourseList())
            System.out.println("Course: " + c.getCourse_code() + " " + c.getCourse_name());
    }

    /**
     * Method to remove Time Slot from existing course
     * @param courseCode Course code of the course to remove
     * @param indexNum Index number of the course.
     * @return Return print message if successful.
     */
    public boolean removeTimeSlot(String courseCode, int indexNum) {

        while(true) {
            int choice, removeChoice, choiceType = -1;
            Course c = dc.getCourseByCode(courseCode);
            Index i = dc.getCourseIndex(c, indexNum);
            Scanner sc = new Scanner(System.in);

            System.out.println("\nType of lessons");
            System.out.println("1. Lecture");
            System.out.println("2. Tutorial");
            System.out.println("3. Lab");
            System.out.println("0. Back");
            System.out.print("Select type of lesson (1,2,3) to remove: ");

            try {
                choiceType = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }

            switch (choiceType) {
                case 1 -> {
                    try {
                        System.out.println("--------Current Lectures--------");
                        if (i.getLessons().get(LessonType.LEC).isEmpty()) {
                            System.out.println("No lectures found");
                        } else {
                            for (int x = 0; x < i.getLecSlots().size(); x++) {
                                TimeSlot ts = i.getLessons().get(LessonType.LEC).get(x);
                                System.out.print((x + 1) + ". ");
                                System.out.println(DayOfWeek.of(ts.getDayOfWeek()) + " (" + ts.getStartTime() + "-" + ts.getEndTime() + ") Location: " + ts.getLocation());
                            }
                            System.out.println("0. Back");
                            System.out.print("Select lesson to remove: ");
                            choice = sc.nextInt();
                            removeChoice = choice - 1;
                            if (removeChoice == -1){
                                continue;
                            }
                            i.getLessons().get(LessonType.LEC).remove(removeChoice);
                            System.out.println("Removed Lecture session successfully!");
                            FileManager.write_all(dc);
                            return true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error! Please enter valid index");
                    }
                }
                case 2 -> {
                    try {
                        System.out.println("--------Current Tutorials--------");
                        if (i.getLessons().get(LessonType.TUT).isEmpty()) {
                            System.out.println("No tutorials found");
                        } else {
                            for (int x = 0; x < i.getTutSlots().size(); x++) {
                                TimeSlot ts = i.getLessons().get(LessonType.TUT).get(x);
                                System.out.print((x + 1) + ". ");
                                System.out.println(DayOfWeek.of(ts.getDayOfWeek()) + " (" + ts.getStartTime() + "-" + ts.getEndTime() + ") Location: " + ts.getLocation());
                            }
                            System.out.println("0. Back");
                            System.out.print("Select lesson to remove: ");
                            choice = sc.nextInt();
                            removeChoice = choice - 1;
                            if (removeChoice == -1){
                                continue;
                            }
                            i.getLessons().get(LessonType.TUT).remove(removeChoice);
                            System.out.println("Removed Tutorial session successfully!");
                            FileManager.write_all(dc);
                            return true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error! Please enter valid index");
                    }
                }
                case 3 -> {
                    try {
                        System.out.println("--------Current Labs--------");
                        if (i.getLessons().get(LessonType.LAB).isEmpty()) {
                            System.out.println("No labs found");
                        } else {
                            for (int x = 0; x < i.getTutSlots().size(); x++) {
                                TimeSlot ts = i.getLessons().get(LessonType.LAB).get(x);
                                System.out.print((x + 1) + ". ");
                                System.out.println(DayOfWeek.of(ts.getDayOfWeek()) + " (" + ts.getStartTime() + "-" + ts.getEndTime() + ") Location: " + ts.getLocation());
                            }
                            System.out.println("0. Back");
                            System.out.print("Select lesson to remove: ");
                            choice = sc.nextInt();
                            removeChoice = choice - 1;
                            if (removeChoice == -1){
                                continue;
                            }
                            i.getLessons().get(LessonType.LAB).remove(removeChoice);
                            System.out.println("Removed Lab session successfully!");
                            FileManager.write_all(dc);
                            return true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error! Please enter valid index");
                    }
                }
                case 0 -> {
                    System.out.println("Going back to previous menu...");
                    return false;
                }
                default -> System.out.println("Invalid option, try again!");
            }
        }
    }

}