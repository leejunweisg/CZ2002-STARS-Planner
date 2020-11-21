package FileManager;

import model.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
        Staff s1 = new Staff("LILYLEE", "password123", "Lily Lee", Gender.F,
                "Singapore", LocalDate.of(1997, 5, 15), "S60011C");
        Staff s2 = new Staff("LIFANG", "password123", "Li Fang", Gender.F,
                "Singapore", LocalDate.of(1989, 1, 1), "S60050D");
        staffList.add(s1);
        staffList.add(s2);

        // hardcoded students
        Student stud1 = new Student("JLEE254", "password123", "Lee Jun Wei", Gender.M,
                "Singapore", LocalDate.of(1997, 5, 15), "U1922896C",
                LocalDate.of(2019, 8, 1) );
        Student stud2 = new Student("EDAN123", "password123", "Edan Ang", Gender.M,
                "Singapore", LocalDate.of(1997, 1, 1), "U1924567C",
                LocalDate.of(2019, 8, 1) );
        Student stud3 = new Student("SAMM111", "password123", "Sammy Tan", Gender.F,
                "Singapore", LocalDate.of(1995, 8, 5), "U1822552C",
                LocalDate.of(2018, 8, 1) );
        studentList.add(stud1);
        studentList.add(stud2);
        studentList.add(stud3);

        // hardcoded CZ2001, and its indexes
        Course c1 = new Course("CZ2001", "Algorithms", School.SCSE);
        Index i1 = new Index(c1, 101050, 5);
        Index i2 = new Index(c1, 101060, 5);

        // create timeslot and add to hashmap->arraylist
        // lec
        TimeSlot t1 = new TimeSlot(1,"N4", LocalTime.of(8,30), LocalTime.of(9,30));
        i1.getLessons().get(LessonType.LEC).add(t1);

        // add indexes to course
        c1.getIndexes().add(i1);
        c1.getIndexes().add(i2);

        // adding students to indexes
        i1.getEnrolledStudents().add(stud1);
        stud1.getRegistered().add(i1);

        i2.getEnrolledStudents().add(stud2);
        stud2.getRegistered().add(i2);

        // hardcode cz2001, no index
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
