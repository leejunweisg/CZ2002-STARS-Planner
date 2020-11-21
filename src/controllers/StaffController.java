package controllers;

import FileManager.DataContainer;
import FileManager.FileManager;
import model.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StaffController {

    private final DataContainer dc;

    public StaffController(DataContainer dc) {
        this.dc = dc;
    }

    // methods for menu tasks
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

    public String setStudentAccess(String matric_number, String startPeriod,String endPeriod){
        // parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy h:m a");
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

    public String createCourse(String courseCode, String courseName, String courseSchool){
        School school = School.valueOf(courseSchool);
        dc.getCourseList().add(new Course(courseCode, courseName, school));

        FileManager.write_all(dc);
        return "Course Added Successfully!";
    }

    public String changeCourseCode(String oldCode, String newCode){
        Course c = dc.getCourseByCode(oldCode);
        c.setCourse_code(newCode);
        FileManager.write_all(dc);
        return "Course code successfully updated!";
    }

    public String changeCourseName(String courseCode, String newName){
        Course c = dc.getCourseByCode(courseCode);
        c.setCourse_name(newName);
        FileManager.write_all(dc);
        return "Course name successfully updated!";
    }

    public String createIndex(String courseCode, int indexNum, int maxCap) {
        Course c = dc.getCourseByCode(courseCode);
        Index newIndex = new Index(c, indexNum, maxCap);

        // add index to course
        c.getIndexes().add(newIndex);

        // write changes to file
        FileManager.write_all(dc);

        return "Index added to course!";
    }

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

    public String addNewTimeSlot(String coursecode, int indexNumber, int lessonType, int dayOfWeek, String location, String startTime, String endTime){

        Course c = dc.getCourseByCode(coursecode);
        Index i = dc.getCourseIndex(c, indexNumber);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:m a");
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


    public String removeTimeSlot(String courseCode, int indexNum){

        int choice, removeChoice;
        Course c = dc.getCourseByCode("CZ2001");
        Index i = dc.getCourseIndex(c, 101050);
        Scanner sc = new Scanner(System.in);


        System.out.println("--------Current Added Lessons--------");
        System.out.println("Course: " + i.getCourse().getCourse_code());
        System.out.println("-------------------------------------");
        System.out.println("LECTURE");

        for(int x =0; x<i.getAllSlots().size();x++) // for LECTURE
        {
            System.out.println(x+1+ ". ");
            System.out.println("day of week: "+i.getLessons().get(LessonType.LEC).get(x).getDayOfWeek());
            System.out.println("start time: "+ i.getLessons().get(LessonType.LEC).get(x).getStartTime());
            System.out.println("end time: " + i.getLessons().get(LessonType.LEC).get(x).getEndTime());
            System.out.println("location: " + i.getLessons().get(LessonType.LEC).get(x).getLocation());
        }
        System.out.println("TUTORIAL");

        if(i.getLessons().get(LessonType.TUT).isEmpty()){
            System.out.println("No Tutorial found");;
        }
        else {
            for (int x = 0; x < i.getAllSlots().size(); x++) // for TUTORIAL
            {
                System.out.println(x + 1 + ". ");
                System.out.println("day of week: " + i.getLessons().get(LessonType.TUT).get(x).getDayOfWeek());
                System.out.println("start time: " + i.getLessons().get(LessonType.TUT).get(x).getStartTime());
                System.out.println("end time: " + i.getLessons().get(LessonType.TUT).get(x).getEndTime());
                System.out.println("location: " + i.getLessons().get(LessonType.TUT).get(x).getLocation());
            }
        }
        System.out.println("LAB");

        if(i.getLessons().get(LessonType.LAB).isEmpty()){
            System.out.println("No Lab found");;
        }
        else {
            for (int x = 0; x < i.getAllSlots().size(); x++) // for LABS
            {
                System.out.println(x + 1 + ". ");

                System.out.println("day of week: " + i.getLessons().get(LessonType.LAB).get(x).getDayOfWeek());
                System.out.println("start time: " + i.getLessons().get(LessonType.LAB).get(x).getStartTime());
                System.out.println("end time: " + i.getLessons().get(LessonType.LAB).get(x).getEndTime());
                System.out.println("location: " + i.getLessons().get(LessonType.LAB).get(x).getLocation());
            }
        }
        System.out.println("Select which to remove (1,2,3...): ");
        choice = sc.nextInt();
        removeChoice = choice - 1;
        i.getLessons().get(LessonType.LEC).remove(removeChoice);
        return "removed successfully!";
    }



    public String checkIndexSlot(String courseCode, int indexNumber){
        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        // retrieve the index from course
        Index i = dc.getCourseIndex(c, indexNumber);

        // return string
        return String.format("\nAvailable: %d\n" + "Total: %d\n" + "In waitlist: %d",
                i.getVacancies(), i.getMax_capacity(), i.getWaitlistedStudents().size());
    }

    public String printByIndex(String courseCode, int indexNumber) {
        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        // get the index
        Index i = dc.getCourseIndex(c, indexNumber);

        // construct string
        if (!i.getEnrolledStudents().isEmpty()) {
            StringBuilder sb;
            sb = new StringBuilder("All registered students:\n");
            for (Student stud : i.getEnrolledStudents())
                sb.append(stud).append("\n");

            return sb.toString();
        }else
            return "There are no students registered for this index!";

    }

    public String printByCourse(String courseCode){

        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        StringBuilder sb;
        int count = 0;
        sb = new StringBuilder("All registered students:\n");
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

    private boolean timeslotClashed(TimeSlot ts1, TimeSlot ts2){
        return ts1.getDayOfWeek() == ts2.getDayOfWeek() && ts1.getStartTime().isBefore(ts2.getEndTime()) && ts2.getStartTime().isBefore(ts1.getEndTime());
    }

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

}