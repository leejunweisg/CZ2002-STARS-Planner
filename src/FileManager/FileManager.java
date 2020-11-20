package FileManager;

import model.Course;
import model.Gender;
import model.Staff;
import model.Student;
import model.School;
import model.Index;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileManager {

    public ArrayList<ArrayList<Object>> read_all(){
        ArrayList<ArrayList<Object>> biglist = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("biglist.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            biglist = (ArrayList<ArrayList<Object>>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to read all");
            force_write();
        }
        return biglist;
    }

    public void write_all(ArrayList<Object> biglist){
        try {
            FileOutputStream fos = new FileOutputStream("biglist.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(biglist);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Staff> read_staff(){
        ArrayList<Staff> staffList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("staff.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            staffList = (ArrayList<Staff>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to load staffs.");
            force_write();
        }

        //System.out.println("Staffs loaded: " + staffList.size());
        return staffList;
    }

    public void write_staff(ArrayList<Staff> staffList){
        try {
            FileOutputStream fos = new FileOutputStream("staff.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(staffList);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Student> read_student(){
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("student.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            studentList = (ArrayList<Student>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to load students.");
            force_write();
        }
        //System.out.println("Students loaded: " + studentList.size());
        return studentList;
    }

    public void write_student(ArrayList<Student> studentList){
        try {
            FileOutputStream fos = new FileOutputStream("student.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(studentList);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Course> read_course(){
        ArrayList<Course> courseList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("course.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            courseList = (ArrayList<Course>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to load courses.");
            force_write();
        }

        //System.out.println("Courses loaded: " + courseList.size());
        return courseList;
    }

    public void write_course(ArrayList<Course> staffList){
        try {
            FileOutputStream fos = new FileOutputStream("course.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(staffList);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // use this method to write empty arraylists to file
    public void force_write(){

        ArrayList<Staff> staffs = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();

        // hardcoded staffs
        Staff s1 = new Staff("LILYLEE", "password123", "Lily Lee", Gender.F, "Singapore", LocalDate.of(1997, 5, 15), "S60011C");
        staffs.add(s1);

        // hardcoded students
        Student stud1 = new Student("JLEE254", "password123", "Lee Jun Wei", Gender.M, "Singapore", LocalDate.of(1997, 5, 15), "U1922896C", LocalDate.of(2019, 1, 1) );
        students.add(stud1);

        // hardcode cz2001's indexes and timeslots
        Course c1 = new Course("CZ2001", "Algorithms", School.SCSE);
        Index i1 = new Index(c1, 101050, 30);
        Index i2 = new Index(c1, 101060, 25);
        c1.getIndexes().add(i1);
        c1.getIndexes().add(i2);
        //TODO hardcode Timeslots here

        // hardcode cz3001, empty index
        Course c2 = new Course("CZ3001", "Advanced Computer Architecture", School.SCSE);
        courses.add(c1);
        courses.add(c2);

        write_staff(staffs);
        write_student(students);
        write_course(courses);

        System.out.println("Hardcoded values saved to file. Please restart application.");
        System.exit(0);
    }


}
