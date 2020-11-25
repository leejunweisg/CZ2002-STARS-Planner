package filemanager;

import model.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A data container class that holds three arraylists for Staff, Student and Course objects.
 * Also contains helper methods, useful for validation.
 */
public class DataContainer implements Serializable {
    /**
     * ArrayList to hold Staff objects.
     */
    private ArrayList<Staff> staffList;
    /**
     * ArrayList to hold Student objects.
     */
    private ArrayList<Student> studentList;
    /**
     * ArrayList to hold Course objects.
     */
    private ArrayList<Course> courseList;

    /**
     * The constructor.
     */
    public DataContainer(){
        System.out.println("Hardcoded DC created");
        staffList = new ArrayList<Staff>();
        studentList = new ArrayList<Student>();
        courseList = new ArrayList<Course>();

        hardcode();
    }

    /**
     * Gets the Staff ArrayList
     * @return Arraylist of Staff objects
     */
    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    /**
     * Gets the Student ArrayList
     * @return Arraylist of Student objects
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Gets the Course ArrayList
     * @return Arraylist of Course objects
     */
    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    /**
     * Hardcodes default data into the arraylists
     */
    private void hardcode(){
        // hardcoded staffs
        Staff s1 = new Staff("LILYLEE", hash("password123"), "Lily Lee", Gender.F,
                "Singapore", LocalDate.of(1997, 5, 15), "S60011C");
        Staff s2 = new Staff("LIFANG", hash("password123"), "Li Fang", Gender.F,
                "Singapore", LocalDate.of(1989, 1, 1), "S60050D");
        staffList.add(s1);
        staffList.add(s2);

        // hardcoded students
        Student stud1 = new Student("JLEE254", hash("password123"), "Lee Jun Wei", Gender.M,
                "Singapore", LocalDate.of(1997, 5, 15), "U1922896C",
                LocalDate.of(2019, 8, 1), "leejunwei.sg@gmail.com");
        Student stud2 = new Student("EDAN123", hash("password123"), "Edan Ang", Gender.M,
                "Singapore", LocalDate.of(1997, 1, 1), "U1924567C",
                LocalDate.of(2019, 8, 1), "edankbf@gmail.com" );
        Student stud3 = new Student("SAMM111", hash("password123"), "Sammy Tan", Gender.F,
                "Singapore", LocalDate.of(1995, 8, 5), "U1822552C",
                LocalDate.of(2018, 8, 1), "asdasd@gmail.com");
        Student stud4 = new Student("SAMC111", hash("password123"), "Sam Cheng", Gender.F,
                "Singapore", LocalDate.of(1995, 8, 15), "U1822552C",
                LocalDate.of(2018, 8, 1), "asdasd@gmail.com");
        Student stud5 = new Student("WTAN153", hash("password123"), "Wei Ang", Gender.M,
                "Singapore", LocalDate.of(1998, 7, 5), "U1924852C",
                LocalDate.of(2019, 8, 1), "pokemontan99@gmail.com");
        Student stud6 = new Student("ICHAY001", hash("password123"), "Ivan", Gender.M,
                "Singapore", LocalDate.of(1998, 6, 5), "U1922236L",
                LocalDate.of(2019, 8, 1), "asdasd@gmail.com");
        Student stud7 = new Student("ATANG003", hash("password123"), "Alvin", Gender.M,
                "Singapore", LocalDate.of(1998, 5, 5), "U1975312F",
                LocalDate.of(2019, 8, 1), "asdasd@gmail.com");
        Student stud8 = new Student("DESM010", hash("password123"), "Desmond", Gender.M,
                "Singapore", LocalDate.of(1995, 4, 5), "U1921252C",
                LocalDate.of(2019, 8, 1), "asdasd@gmail.com");
        Student stud9 = new Student("XYZA142", hash("password123"), "Xin Yu", Gender.M,
                "Singapore", LocalDate.of(1995, 3, 5), "U1912345G",
                LocalDate.of(2019, 8, 1), "asdasd@gmail.com");
        Student stud10 = new Student("LUCI211", hash("password123"), "Lucio", Gender.M,
                "Singapore", LocalDate.of(1995, 2, 5), "U1995132E",
                LocalDate.of(2019, 8, 1), "asdasd@gmail.com");
        Student stud11 = new Student("SYS001", hash("password123"), "Siong Yew", Gender.M,
                "Singapore", LocalDate.of(1995, 1, 5), "U1963851B",
                LocalDate.of(2019, 8, 1), "pokemontan99@gmail.com");
        Student stud12 = new Student("SKOH111", hash("password123"), "Sean", Gender.M,
                "Singapore", LocalDate.of(1995, 8, 5), "U1863542C",
                LocalDate.of(2018, 8, 1), "asdasd@gmail.com");
        studentList.add(stud1);
        studentList.add(stud2);
        studentList.add(stud3);
        studentList.add(stud4);
        studentList.add(stud5);
        studentList.add(stud6);
        studentList.add(stud7);
        studentList.add(stud8);
        studentList.add(stud9);
        studentList.add(stud10);
        studentList.add(stud12);
        studentList.add(stud11);
        studentList.add(stud12);

        // hardcoded CZ2001, and its indexes
        Course c1 = new Course("CZ2001", "Algorithms", School.SCSE, 3);

        // hardcoded indexes/time for cz2001
        Index i1 = new Index(c1, 101050, 5);
        TimeSlot t1 = new TimeSlot(1,"N4", LocalTime.of(8,30), LocalTime.of(9,30));
        TimeSlot t4 = new TimeSlot(3,"N4", LocalTime.of(8,30), LocalTime.of(9,30));
        i1.getLessons().get(LessonType.LEC).add(t1);
        i1.getLessons().get(LessonType.LEC).add(t4);

        Index i2 = new Index(c1, 101060, 10);
        TimeSlot t2 = new TimeSlot(1,"N8", LocalTime.of(8,30), LocalTime.of(9,30));
        i2.getLessons().get(LessonType.LEC).add(t2);

        // add indexes to c2001
        c1.getIndexes().add(i1);
        c1.getIndexes().add(i2);

        // adding students to indexes of cz2001
        i1.getEnrolledStudents().add(stud1);
        stud1.getRegistered().add(i1);

        i2.getEnrolledStudents().add(stud2);
        stud2.getRegistered().add(i2);

        i2.getEnrolledStudents().add(stud3);
        stud3.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud4);
        stud4.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud6);
        stud6.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud7);
        stud7.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud8);
        stud8.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud9);
        stud9.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud10);
        stud10.getRegistered().add(i2);
        
        i2.getEnrolledStudents().add(stud11);
        stud11.getRegistered().add(i2);
        
        i1.getEnrolledStudents().add(stud12);
        stud12.getRegistered().add(i1);

        // hardcode cz2001, no index
        Course c2 = new Course("CZ3001", "Advanced Computer Architecture", School.SCSE, 3);
        Index i3 = new Index(c2, 102050, 10);
        TimeSlot t3 = new TimeSlot(1,"N6", LocalTime.of(9,0), LocalTime.of(10,0));
        i3.getLessons().get(LessonType.LEC).add(t3);
        c2.getIndexes().add(i3);

        // hardcode cz2004, for full waitlist
        Course c3 = new Course("CZ2004", "Human Computer Interaction", School.SCSE, 3);
        Index i4 = new Index(c3, 102205, 10);
        TimeSlot t5 = new TimeSlot(5,"N6", LocalTime.of(15,0), LocalTime.of(17,0));
        i4.getLessons().get(LessonType.LEC).add(t5);
        c3.getIndexes().add(i4);
        
        i4.getEnrolledStudents().add(stud1);
        stud1.getRegistered().add(i4);

        i4.getEnrolledStudents().add(stud2);
        stud2.getRegistered().add(i4);

        i4.getEnrolledStudents().add(stud3);
        stud3.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud4);
        stud4.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud6);
        stud6.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud7);
        stud7.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud8);
        stud8.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud9);
        stud9.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud10);
        stud10.getRegistered().add(i4);
        
        i4.getEnrolledStudents().add(stud11);
        stud11.getRegistered().add(i4);
        
        Course c4 = new Course("CZ2005", "Operating Systems", School.SCSE, 3);
        Index i5 = new Index(c4, 105050, 10);
        TimeSlot t6 = new TimeSlot(3,"N4", LocalTime.of(12,0), LocalTime.of(13,0));
        TimeSlot t7 = new TimeSlot(5,"N4", LocalTime.of(13,0), LocalTime.of(14,0));
        i5.getLessons().get(LessonType.LEC).add(t6);
        i5.getLessons().get(LessonType.LEC).add(t7);
        c4.getIndexes().add(i5);
        
        i5.getEnrolledStudents().add(stud5);
        stud5.getRegistered().add(i5);
        
        i5.getEnrolledStudents().add(stud11);
        stud11.getRegistered().add(i5);
        
        Index i6 = new Index(c4, 105000, 10);
        TimeSlot t8 = new TimeSlot(2,"N6", LocalTime.of(12,0), LocalTime.of(13,0));
        TimeSlot t9 = new TimeSlot(4,"N6", LocalTime.of(13,0), LocalTime.of(14,0));
        i6.getLessons().get(LessonType.LEC).add(t8);
        i6.getLessons().get(LessonType.LEC).add(t9);
        c4.getIndexes().add(i6);
        
        i6.getEnrolledStudents().add(stud4);
        stud4.getRegistered().add(i6);
        
        i6.getEnrolledStudents().add(stud6);
        stud6.getRegistered().add(i6);
        
        
        // add courses
        courseList.add(c1);
        courseList.add(c2);
        courseList.add(c3);
        courseList.add(c4);
    }

    // validation methods to be used from the UI

    /**
     * Checks if an username exists.
     * @param username The username to check.
     * @return Returns true if exists, else false.
     */
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

    /**
     * Checks if a matriculation number exists.
     * @param matric_number The matriculation number to check.
     * @return Returns true if exists, else false.
     */
    public boolean existMatricNumber(String matric_number){
        // read latest students from file
        for (Student stud: studentList)
            if (stud.getMatric_number().equals(matric_number))
                return true;

        return false;
    }

    /**
     * Checks if a school exists.
     * @param school The school to check.
     * @return Returns true if exists, else false.
     */
    public boolean existSchool(String school){
        try{
            School.valueOf(school);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * Checks if a course with the course code exists.
     * @param courseCode The course code to check.
     * @return Returns true if exists, else false.
     */
    public boolean existCourse(String courseCode){
        for (Course c: courseList){
            if (c.getCourse_code().equals(courseCode.toUpperCase()))
                return true;
        }
        return false;
    }

    /**
     * Checks if a course Index exists in a course.
     * @param courseCode The course code to check.
     * @param indexNumber The index number to check.
     * @return Returns true if exists, else false.
     */
    public boolean existIndexInCourse(String courseCode, int indexNumber){
        Course c = getCourseByCode(courseCode);
        for (Index i:c.getIndexes())
            if (i.getIndex_number() == indexNumber)
                return true;
        return false;
    }

    /**
     * Checks if an email exists.
     * @param email The email to check.
     * @return Returns true if exists, else false.
     */
    public boolean existEmail(String email){
        for (Student stud: studentList)
            if (stud.getEmail().toUpperCase().equals(email.toUpperCase()))
                return true;
        return false;
    }

    // helper methods

    /**
     * Get the Student object with the matriculation number.
     * @param matric The matriculation number.
     * @return Returns the Student object.
     */
    public Student getStudentByMatric(String matric){
        for (Student stud: studentList)
            if (stud.getMatric_number().equals(matric))
                return stud;
        return null;
    }

    /**
     * Get the Course object with the course code.
     * @param courseCode The course code.
     * @return Returns the Course object.
     */
    public Course getCourseByCode(String courseCode){
        for(Course course: courseList)
        {
            if(course.getCourse_code().equals(courseCode))
                return course;
        }
        return null;
    }

    /**
     * Get the course Index of a course with the index number.
     * @param c The Course object.
     * @param indexNumber The index number.
     * @return Returns the Index object.
     */
    public Index getCourseIndex(Course c, int indexNumber){
        for (Index i: c.getIndexes()){
            if (i.getIndex_number() == indexNumber)
                return i;
        }
        return null;
    }

    /**
     * Get the Student object with the username.
     * @param username The student's username.
     * @return Returns the Student object.
     */
    public Student getStudentByUsername(String username){
        for (Student stud: studentList)
            if (stud.getUsername().equals(username))
                return stud;
        return null;
    }

    /**
     * Hashes a string using SHA-256.
     * @param passwordStr The password string.
     * @return Returns the hash in a byte array.
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
}
