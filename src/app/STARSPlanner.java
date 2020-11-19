package app;

import controllers.LoginController;
import controllers.StaffController;
import controllers.StudentController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class STARSPlanner {

    private LoginController login_controller;
    private StudentController student_controller;
    private StaffController staff_controller;
    private Scanner sc = new Scanner(System.in);

    public STARSPlanner() {
        login_controller = new LoginController();
        student_controller = new StudentController();
        staff_controller = new StaffController();
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
        int choice=0;
        do{
            System.out.printf("\n[Admin Menu] Logged in as: %s\n", username);
            System.out.println("1. Add a student");
            System.out.println("2. Edit student access period");
            System.out.println("3. Create new course");
            System.out.println("4. Update a course");
            System.out.println("5. Check available slot for an index number in a Course");
            System.out.println("6. Print Student list by index number");
            System.out.println("7. Print student list by course");
            System.out.println("0. Log out");

            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch(choice){
                case 1 -> addStudent();
                case 2 -> editStudentAccess();
                case 3 -> createCourse();
                case 4 -> updateCourse();
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

        System.out.println("\n[Add a new Student]");

        // username
        while (true){
            System.out.print("Enter username: ");
            username = sc.nextLine().toUpperCase();
            if (username.isEmpty())
                System.out.println("You need to enter something!");
            else if (staff_controller.existUsername(username))
                System.out.println("Username already exists!");
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
            else if(staff_controller.existMatricNumber(matric_number))
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

        System.out.println("\n[Edit student access period]");

        // matric_number
        while (true){
            System.out.print("Enter matric number of student: ");
            matric_number = sc.nextLine().toUpperCase();

            // use regex to check if matric number entered matches format
            Pattern pattern = Pattern.compile("^[A-Z][0-9]{7}[A-Z]$");
            Matcher matcher = pattern.matcher(matric_number);

            if (!matcher.matches())
                System.out.println("Invalid matric number!");
            else if(staff_controller.existMatricNumber(matric_number))
                System.out.println("Student was not added, matriculation number already exists!");
            else
                break;
        }

        // start period
        while (true){
            System.out.print("Enter start of period (dd/mm/yyyy hh:mm): ");
            startPeriod = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
                LocalDate.parse(startPeriod, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid format!");
            }
        }

        // end date time
        while (true){
            System.out.print("Enter end of period (dd/mm/yyyy hh:mm): ");
            endPeriod = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
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

        System.out.println("\n[Create Course]");

        String courseCode, courseName, courseSchool;

        while(true){
            try{
                System.out.print("Enter New Course Code: ");
                courseCode = sc.nextLine();

                if(courseCode.toLowerCase().equals("exit")){
                    System.out.println("exit(). press enter to go back to main menu");
                    sc.nextLine();
                    return;
                }
                break;
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        while(true){
            try{
                System.out.print("Enter New Course Name: ");
                courseName = sc.nextLine();
                break;
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        while(true){
            try{
                System.out.print("Enter School: ");
                courseSchool = sc.nextLine();
                if (!staff_controller.existSchool(courseSchool))
                    System.out.println("error");
                //TODO School enum validation
                break;
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        System.out.println(staff_controller.createCourse(courseCode,courseName,courseSchool));


    }

    private void updateCourse(){
        //TODO updateCourse()
        int choice=0;
        do{
            System.out.println("\n[Update Course]");
            System.out.println("1. Create Index for a Course");
            System.out.println("2. Remove Index from a Course");
            System.out.println("0. Back");

            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch(choice){
                case 1 -> createIndex();
                case 2 -> removeIndex();
            }
        }while(choice !=0);
    }

    private void createIndex(){

        System.out.println("\n[Create Index]");
        //call staff controller
        String courseCode, indexNumber, maxCapacity;

        while(true) {
            try {
                System.out.println("Enter Course Code: ");
                //TODO Check if course code exist
                courseCode = sc.nextLine();
                break;
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        while(true) {
            try {
                System.out.println("Enter Index Number: ");
                //TODO Check if course code exist
                indexNumber = sc.nextLine();
                break;
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        while(true) {
            try {
                System.out.println("Enter Course maximum Capacity: ");
                //TODO Check if course code exist
                maxCapacity = sc.nextLine();
                break;
            }catch(Exception e){
                System.out.println("Error");
            }
        }

        System.out.println(staff_controller.createIndex(courseCode,indexNumber,maxCapacity));

    }

    private void removeIndex(){
        System.out.println("\n[Remove Index]");
        //call staff controller
    }

    private void checkIndexSlots(){
        System.out.println("\n[Check available slot for an index number]");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!staff_controller.existCourse(courseCode))
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

        System.out.println(staff_controller.checkIndexSlot(courseCode,indexNumber));
    }

    private void printByIndex(){
        System.out.println("\n[Print Student list by index number]");

        String courseCode;
        while (true){
            System.out.print("Enter Course Code: ");
            courseCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(courseCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(!staff_controller.existCourse(courseCode))
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

        System.out.println(staff_controller.printByIndex(courseCode, indexNumber));

    }

    private void printByCourse(){
        //TODO check printByCourse() after courses enrolment is implemented
    }

    private void studentMenu(String username){
        System.out.println("\nHi student! Logged in as: " + username);
    }

}
