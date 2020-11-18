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

    public ArrayList<Staff> read_staff(){
        ArrayList<Staff> staffList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("staff.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            staffList = (ArrayList<Staff>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to load staffs.");
            force_write(1);
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
            force_write(2);
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
            FileInputStream fis = new FileInputStream("staff.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            courseList = (ArrayList<Course>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to load courses.");
            force_write(3);
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
    public void force_write(int which){
        switch(which){
            case 1:
                ArrayList<Staff> staff = new ArrayList<>();
                staff.add(new Staff("LILYLEE", "password123", "Lily Lee", Gender.F, "Singapore", LocalDate.of(1997, 5, 15), "S60011C"));
                write_staff(staff);
                break;

            case 2:
                ArrayList<Student> students = new ArrayList<>();
                students.add(new Student("JLEE254", "password123", "Lee Jun Wei", Gender.M, "Signapore", LocalDate.of(1997, 5, 15), "U1922896C", LocalDate.of(2019, 1, 1) ));
                write_student(students);
                break;

            case 3:
                ArrayList<Course> course = new ArrayList<>();
                course.add(new Course("CZ2001", "Algorithm", School.SCSE));
                write_course(course);
                break;
            case 4:
                ArrayList<Index> index = new ArrayList<>();

        }
        System.out.println("Clean slate executed!");
    }

    public ArrayList<Index> read_index(){
        ArrayList<Index> indexList = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream("index.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            indexList = (ArrayList<Index>) in.readObject();
            in.close();
        }catch (IOException | ClassNotFoundException ex){
            System.out.println("Failed to load courses.");
            force_write(4);
        }
        return indexList;
    }

    public void write_array(ArrayList<Index> indexList){
        try {
            FileOutputStream fos = new FileOutputStream("index.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(indexList);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
