package FileManager;

import model.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataContainer implements Serializable {

    private ArrayList<Staff> staffList;
    private ArrayList<Student> studentList;
    private ArrayList<Course> courseList;

    public DataContainer(){
        System.out.println("Hardcoded DC created");
        staffList = new ArrayList<Staff>();
        studentList = new ArrayList<Student>();
        courseList = new ArrayList<Course>();

        hardcode();

    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    private void hardcode(){
        // hardcoded staffs
        Staff s1 = new Staff("LILYLEE", "password123", "Lily Lee", Gender.F, "Singapore", LocalDate.of(1997, 5, 15), "S60011C");
        staffList.add(s1);

        // hardcoded students
        Student stud1 = new Student("JLEE254", "password123", "Lee Jun Wei", Gender.M, "Singapore", LocalDate.of(1997, 5, 15), "U1922896C", LocalDate.of(2019, 1, 1) );
        studentList.add(stud1);

        // hardcode cz2001's indexes and timeslots
        Course c1 = new Course("CZ2001", "Algorithms", School.SCSE);
        Index i1 = new Index(c1, 101050, 30);
        Index i2 = new Index(c1, 101060, 25);
        c1.getIndexes().add(i1);
        c1.getIndexes().add(i2);
        //TODO hardcode Timeslots here

        // hardcode cz3001, empty index
        Course c2 = new Course("CZ3001", "Advanced Computer Architecture", School.SCSE);
        courseList.add(c1);
        courseList.add(c2);
    }

    // validation methods to be used from the UI
    public boolean existUsername(String username){
        // read latest students from file
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
        for (Course c: courseList){
            if (c.getCourse_code().equals(courseCode.toUpperCase()))
                return true;
        }
        return false;
    }

    public boolean existIndexInCourse(String courseCode, int indexNumber){
        Course c = getCourseByCode(courseCode);
        for (Index i:c.getIndexes())
            if (i.getIndex_number() == indexNumber)
                return true;
        return false;
    }

    // helper methods
    public Student getStudentByMatric(String matric){
        for (Student stud: studentList)
            if (stud.getMatric_number().equals(matric))
                return stud;
        return null;
    }

    public Course getCourseByCode(String courseCode){
        for(Course course: courseList)
        {
            if(course.getCourse_code().equals(courseCode))
                return course;
        }
        return null;
    }

    public Index getCourseIndex(Course c, int indexNumber){
        for (Index i: c.getIndexes()){
            if (i.getIndex_number() == indexNumber)
                return i;
        }
        return null;
    }

    public Student getStudentByUsername(String username){
        for (Student stud: studentList)
            if (stud.getUsername().equals(username))
                return stud;
        return null;
    }
}
