package controllers;

import FileManager.FileManager;
import model.*;
import FileManager.DataContainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StaffController {

    private final DataContainer dc;

    public StaffController(DataContainer dc) {
        this.dc = dc;
    }

    // methods for menu tasks
    public String addStudent(String username, String passwordPlain, String fullname, String gender, String nationality, String dob, String matric_number, String matriculation_date){
        // convert variables to required types
        Gender genderEnum = Gender.valueOf(gender);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dobParsed = LocalDate.parse(dob, formatter);
        LocalDate matricDateParsed = LocalDate.parse(matriculation_date, formatter);

        // add student
        dc.getStudentList().add(new Student(username, passwordPlain, fullname, genderEnum, nationality, dobParsed, matric_number, matricDateParsed));

        // write changes to file
        FileManager.write_all(dc);
        return "Student added successfully!";
    }

    public String setStudentAccess(String matric_number, String startPeriod,String endPeriod){
        // parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
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

//    public String createIndex(String courseCode, String indexNum, String maxCap) {
//        //indexList = fm.read_index();
//        courseList =fm.read_course();
//        Course course = getCourseByCode(courseCode);
//
//        int indexNumber = Integer.parseInt(indexNum);
//        int maxCapacity = Integer.parseInt(maxCap);
//        if(course!=null) {
//            ArrayList<Index> indexList = course.getIndexes(); // gives current array list of indexes
//            indexList.add(new Index(course, indexNumber, maxCapacity));
//            course.setIndexes(indexList);
//            fm.write_course(courseList);
//            return "Success!";
//        }
//        else{
//            return "failed";
//        }
//    }
//
    public String checkIndexSlot(String courseCode, int indexNumber){
        // read latest courses from file

        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        // find the index
        if (c != null) {
            for (Index i: c.getIndexes()) {
                if (i.getIndex_number() == indexNumber)
                    return String.format("\nCourse: %s %s \t Index: %d \t Capacity: %d/%d", courseCode, c.getCourse_name(),
                            indexNumber, i.getEnrolledStudents().size(), i.getMax_capacity());
            }
        }

        // index not found
        return "The index number you entered was not found in the course.";
    }

    public String printByIndex(String courseCode, int indexNumber) {
        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        // if course does not have any index
        if (c != null && c.getIndexes() == null)
            return "This course has no indexes!";

        // find the index
        StringBuilder sb;
        if (c != null)
            for (Index i : c.getIndexes())
                if (i.getIndex_number() == indexNumber) {
                    sb = new StringBuilder("All registered students:\n");

                    for (Student stud : i.getEnrolledStudents())
                        sb.append(stud).append("\n");
                    return sb.toString();
                }


        return "The index number you entered was not found in the course.";
    }

    public String printByCourse(String courseCode){

        // retrieve the course
        Course c = dc.getCourseByCode(courseCode);

        StringBuilder sb;
        sb = new StringBuilder("All registered students:\n");
        if (c != null)
            for (Index i : c.getIndexes()) {
                for (Student stud : i.getEnrolledStudents())
                    sb.append(String.format("%d \t %s\n",  i.getIndex_number(), stud));
            }

        return sb.toString();
    }


}