package app;

import FileManager.FileManager;
import controllers.LoginController;
import controllers.StaffController;
import controllers.StudentController;
import FileManager.DataContainer;
import jdk.swing.interop.SwingInterOpUtils;
import model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class STARSPlanner {

    private LoginController login_controller;
    private StudentController student_controller;
    private StaffController staff_controller;
    private DataContainer data_container;
    private Scanner sc = new Scanner(System.in);

    public STARSPlanner() {
        data_container = FileManager.read_all();
        login_controller = new LoginController(data_container);
        student_controller = new StudentController(data_container);
        staff_controller = new StaffController(data_container);
    }

    public void run() {
        int status = 0;
        do {
            System.out.println("\nWelcome to STARS Planner!");
            System.out.println("Please login, or enter -1 to exit\n");

            System.out.print("USERNAME: ");
            String username = sc.nextLine().toUpperCase();

            if (username.equals("-1"))
                return;

            System.out.print("PASSWORD: ");
            String password = sc.nextLine();

            status = login_controller.authenticate(username, password);

            switch(status){
                case 0 -> System.out.println("Invalid username or password!");
                case 1 -> adminMenu(username);
                case 2 -> studentMenu(username);
            }

        }while(true);
    }

    private void adminMenu(String username) {
        int choice=-1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.printf("[Admin Menu] Logged in as: %s\n", username);
            System.out.println("1. Add a student");
            System.out.println("2. Set access period for a student");
            System.out.println("3. Create new course");
            System.out.println("4. Update a course");
            System.out.println("5. Check slot availability for an index number in a Course");
            System.out.println("6. Print Student list by index number");
            System.out.println("7. Print student list by course");
            System.out.println("0. Log out");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch(Exception e){
                continue;
            }

            switch(choice){
                case 1 -> addStudent();
                case 2 -> editStudentAccess();
                case 3 -> createCourse();
//                case 4 -> updateCourse();
                case 5 -> checkIndexSlots();
                case 6 -> printByIndex();
                case 7 -> printByCourse();
            }
        }while(choice !=0);
    }

    private void addStudent(){
        String username;
        String password;
        String fullname;
        String gender;
        String nationality;
        String dob;
        String matric_number;
        String matriculation_date;

        System.out.println("\n-> Add a new Student");

        // username
        while (true){
            System.out.print("Enter username: ");
            username = sc.nextLine().toUpperCase();
            if (username.isEmpty())
                System.out.println("You need to enter something!");
            else if (data_container.existUsername(username))
                System.out.println("Username already exists, enter another!");
            else
                break;
        }

        // password
        while (true){
            System.out.print("Enter password: ");
            password = sc.nextLine();
            if (password.isEmpty())
                System.out.println("You need to enter something!");
            else
                break;
        }

        // fullname
        while (true){
            System.out.print("Enter fullname: ");
            fullname = sc.nextLine();
            if (fullname.isEmpty())
                System.out.println("You need to enter something!");
            else
                break;
        }

        // gender
        while (true){
            System.out.print("Enter gender (M/F): ");
            gender = sc.nextLine().toUpperCase();
            if (!(gender.equals("M") || gender.equals("F")))
                System.out.println("Invalid input, try again!");
            else
                break;
        }

        // nationality
        while (true){
            System.out.print("Enter nationality: ");
            nationality = sc.nextLine();
            if (nationality.isEmpty())
                System.out.println("You need to enter something!");
            else
                break;
        }

        // dob
        while (true){
            System.out.print("Enter date of birth (dd/mm/yyyy): ");
            dob = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate.parse(dob, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid format!");
            }
        }

        // matric number
        while (true){
            System.out.print("Enter matric number: ");
            matric_number = sc.nextLine().toUpperCase();

            // use regex to check if matric number entered matches format
            Pattern pattern = Pattern.compile("^[A-Z][0-9]{7}[A-Z]$");
            Matcher matcher = pattern.matcher(matric_number);

            if (!matcher.matches())
                System.out.println("Invalid matric number!");
            else if(data_container.existMatricNumber(matric_number))
                System.out.println("Student was not added, matriculation number already exists!");
            else
                break;
        }

        // matric date
        while (true){
            System.out.print("Enter matriculation date (dd/mm/yyyy): ");
            matriculation_date = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate.parse(matriculation_date, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid format!");
            }
        }

        // call method in STARSPlanner to add student, print result
        System.out.println(staff_controller.addStudent(username, password, fullname, gender, nationality, dob, matric_number, matriculation_date));
    }

    private void editStudentAccess(){
        String matric_number;
        String startPeriod;
        String endPeriod;

        System.out.println("\n-> Edit student access period");

        // matric_number
        while (true){
            System.out.print("Enter matric number of student: ");
            matric_number = sc.nextLine().toUpperCase();

            // use regex to check if matric number entered matches format
            Pattern pattern = Pattern.compile("^[A-Z][0-9]{7}[A-Z]$");
            Matcher matcher = pattern.matcher(matric_number);

            if (!matcher.matches())
                System.out.println("Invalid matric number!");
            else if(!data_container.existMatricNumber(matric_number)) {
                System.out.println("A student with that matriculation does not exist!");
                return;
            } else
                break;
        }

        // start period
        while (true){
            System.out.print("Enter start of period (dd/mm/yyyy hh:mm am/pm): ");
            startPeriod = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy hh:mm a");
                LocalDate.parse(startPeriod, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid format!");
            }
        }

        // end date time
        while (true){
            System.out.print("Enter end of period (dd/mm/yyyy hh:mm am/pm): ");
            endPeriod = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy hh:mm a");
                LocalDate.parse(endPeriod, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid format!");
            }
        }

        // set period, and print result
        System.out.println(staff_controller.setStudentAccess(matric_number, startPeriod, endPeriod));
    }

    private void createCourse(){
        System.out.println("\n-> Create Course");

        String courseCode, courseName, courseSchool;

        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(data_container.existCourse(courseCode)) {
                System.out.println("Course code already exists!");
                return;
            }else
                break;
        }

        while(true){
            System.out.print("Enter New Course Name: ");
            courseName = sc.nextLine();
            if (courseName.isEmpty())
                System.out.println("You need to enter something!");
            else
                break;
        }

        while(true){
            System.out.print("Enter School: ");
            courseSchool = sc.nextLine().toUpperCase();
            if (courseSchool.isEmpty())
                System.out.println("You need to enter something!");
            else if (!data_container.existSchool(courseSchool))
                System.out.println("School does not exist!");
            else
                break;
        }

        System.out.println(staff_controller.createCourse(courseCode,courseName,courseSchool));
    }

//    private void updateCourse(){
//        //TODO updateCourse()
//        int choice=0;
//        do{
//            System.out.println("\n[Update Course]");
//            System.out.println("1. Create Index for a Course");
//            System.out.println("2. Remove Index from a Course");
//            System.out.println("0. Back");
//
//            System.out.print("\nEnter choice: ");
//            choice = Integer.parseInt(sc.nextLine());
//
//            switch(choice){
//                case 1 -> createIndex();
//                case 2 -> removeIndex();
//            }
//        }while(choice !=0);
//    }
//
//    private void createIndex(){
//
//        System.out.println("\n[Create Index]");
//        //call staff controller
//        String courseCode, indexNumber, maxCapacity;
//
//        while(true) {
//            try {
//                System.out.println("Enter Course Code: ");
//                //TODO Check if course code exist
//                courseCode = sc.nextLine();
//                break;
//            }catch(Exception e){
//                System.out.println("Error");
//            }
//        }
//        while(true) {
//            try {
//                System.out.println("Enter Index Number: ");
//                //TODO Check if course code exist
//                indexNumber = sc.nextLine();
//                break;
//            }catch(Exception e){
//                System.out.println("Error");
//            }
//        }
//        while(true) {
//            try {
//                System.out.println("Enter Course maximum Capacity: ");
//                //TODO Check if course code exist
//                maxCapacity = sc.nextLine();
//                break;
//            }catch(Exception e){
//                System.out.println("Error");
//            }
//        }
//
//        System.out.println(staff_controller.createIndex(courseCode,indexNumber,maxCapacity));
//
//    }
//
//    private void removeIndex(){
//        System.out.println("\n[Remove Index]");
//        //call staff controller
//    }

    private void checkIndexSlots(){
        System.out.println("\n-> Check available slot for an index number");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode))
                System.out.println("Course code is not found!");
            else
                break;
        }

        int indexNumber;
        while(true) {
            System.out.print("Enter Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                indexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (!data_container.existIndexInCourse(courseCode, indexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        System.out.println(staff_controller.checkIndexSlot(courseCode,indexNumber));
    }

    private void printByIndex() {
        System.out.println("\n-> Print Student list by index number");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode)) {
                System.out.println("Course code is not found!");
                return;
            }else
                break;
        }

        int indexNumber;
        while(true) {
            System.out.print("Enter Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                indexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (!data_container.existIndexInCourse(courseCode, indexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        System.out.println(staff_controller.printByIndex(courseCode, indexNumber));
    }

    private void printByCourse(){
        System.out.println("\n-> Print Student list by Course");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode))
                System.out.println("Course code is not found!");
            else
                break;
        }

        System.out.println(staff_controller.printByCourse(courseCode));
    }

    private void studentMenu(String username){
        int choice=-1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.printf("[Student Menu] Logged in as: %s\n", username);
            System.out.println("1. Register for a Course");
            System.out.println("2. Drop Registered Course");
            System.out.println("3. Drop Waitlisted Course");
            System.out.println("4. Display Registered/Waitlisted Courses");
            System.out.println("5. Check Vacancies Available");
            System.out.println("6. Change Index Number of Registered Course");
            System.out.println("7. Swop Index Number with Another Student");
            System.out.println("0. Log out");
            System.out.println("----------------------------------------------------");

            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch(Exception e){
                continue;
            }

            switch(choice){
                case 1 -> registerForCourse(username);
                case 2 -> dropRegisteredCourse(username);
                case 3 -> dropWaitlistedCourse(username);
                case 4 -> displayStudentCourse(username);
                case 5 -> checkIndexSlots();
                case 6 -> changeIndex(username);
                case 7 -> swapIndex(username);
            }
        }while(choice !=0);
    }

    private void registerForCourse(String username){
        System.out.println("\n-> Register For a Course");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode))
                System.out.println("Course code is not found!");
            else
                break;
        }

        int indexNumber;
        while(true) {
            System.out.print("Enter Index Number: ");

            // ensure user input is a valid index number (does not mean it exists)
            try{
                indexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }
            break;
        }

        System.out.println(student_controller.registerForCourse(username, courseCode,indexNumber));
    }

    private void dropRegisteredCourse(String username){
        System.out.println("\n-> Drop a Registered Course");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode)) {
                System.out.println("Course code is not found!");
                return;
            }else
                break;
        }

        int indexNumber;
        while(true) {
            System.out.print("Enter Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                indexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (!data_container.existIndexInCourse(courseCode, indexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        System.out.println(student_controller.dropRegisteredCourse(username, courseCode, indexNumber));

    }

    private void dropWaitlistedCourse(String username){
        System.out.println("\n-> Drop a Waitlisted Course");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode)) {
                System.out.println("Course code is not found!");
                return;
            }else
                break;
        }

        int indexNumber;
        while(true) {
            System.out.print("Enter Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                indexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (!data_container.existIndexInCourse(courseCode, indexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        System.out.println(student_controller.dropWaitlistedCourse(username, courseCode, indexNumber));

    }

    private void displayStudentCourse(String username){
        System.out.println("\n-> Registered/Waitlisted Courses");
        System.out.println(student_controller.displayStudentCourse(username));
    }

    private void changeIndex(String username){
        System.out.println("\n-> Change Index");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode)) {
                System.out.println("Course code is not found!");
                return;
            }else
                break;
        }

        int oldIndexNumber;
        while(true) {
            System.out.print("Enter Old Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                oldIndexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (!data_container.existIndexInCourse(courseCode, oldIndexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        int newIndexNumber;
        while(true) {
            System.out.print("Enter New Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                newIndexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (oldIndexNumber == newIndexNumber) {
                System.out.println("Old index cannot be the same as new index!");
                return;
            }if (!data_container.existIndexInCourse(courseCode, newIndexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        System.out.println(student_controller.changeIndex(username, courseCode, oldIndexNumber, newIndexNumber));
    }

    private void swapIndex(String username){
        System.out.println("\n-> Swap Index Number with Another Student");

        // login for the other student
        System.out.print("USERNAME: ");
        String username2 = sc.nextLine().toUpperCase();

        System.out.print("PASSWORD: ");
        String password2 = sc.nextLine();

        int status = login_controller.authenticate(username2, password2);
        switch(status){
            case 2: //student
                System.out.println("Verified!");
                break;
            case 0: //invalid
                System.out.println("Invalid username or password!");
                return;
            case 1: //staff
                System.out.println("The account is not a student account!");
                return;
        }

        // enter course
        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!data_container.existCourse(courseCode)) {
                System.out.println("Course code is not found!");
                return;
            }else
                break;
        }

        int oldIndexNumber;
        while(true) {
            System.out.print("Enter Old Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                oldIndexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (!data_container.existIndexInCourse(courseCode, oldIndexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        int newIndexNumber;
        while(true) {
            System.out.print("Enter New Index Number: ");

            // ensure user input is a valid index number in the right format
            try{
                newIndexNumber = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid index number!");
                continue;
            }

            // check if index number exists in the course
            if (oldIndexNumber == newIndexNumber) {
                System.out.println("Old index cannot be the same as new index!");
                return;
            }if (!data_container.existIndexInCourse(courseCode, newIndexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        // try swap
        System.out.println(student_controller.swapIndex(username, username2,courseCode, oldIndexNumber,newIndexNumber ));
    }
}
