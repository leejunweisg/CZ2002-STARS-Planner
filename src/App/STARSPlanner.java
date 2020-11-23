package App;

import FileManager.DataContainer;
import FileManager.FileManager;
import Controller.LoginController;
import Controller.StaffController;
import Controller.StudentController;

import java.io.Console;
import java.time.LocalDate;
import java.time.LocalTime;
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

            // if user wants to quit
            if (username.equals("-1"))
                return;

            // use console for password masking, tested to be working on window's cmd and mac's zsh shell
            // does not work on eclipse/intelliJ's integrated terminal
            Console cn = System.console();
            String password;

            // revert to normal scanner input for unsupported terminals
            if (cn!=null)
                password = new String(cn.readPassword("PASSWORD: "));
            else {
                System.out.print("PASSWORD (unsupported terminal, password not masked): ");
                password = sc.nextLine();
            }

            // authenticate
            status = login_controller.authenticate(username, password);

            // authenticate result
            switch(status){
                case 0 -> System.out.println("Invalid username or password!");
                case 1 -> adminMenu(username);
                case 2 -> studentMenu(username);
                case 3 -> System.out.println("You are not allowed to access STARS at this time!");
            }

        }while(true);
    }
//---------------------------- ADMIN MENU ----------------------------//

    private void adminMenu(String username) {
        int choice=-1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.printf("[Admin Menu] Logged in as: %s\n", username);
            System.out.println("1. Add a Student");
            System.out.println("2. Set Access Period for a Student");
            System.out.println("3. Create New Course");
            System.out.println("4. Update a Course");
            System.out.println("5. Check Slot Availability for a Course Index Number");
            System.out.println("6. List Students by Course Index Number");
            System.out.println("7. List Students by Course");
            System.out.println("0. Log out");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch(Exception e){
                System.out.println("Enter numbers only!");
                continue;
            }

            switch(choice){
                case 1 -> addStudent();
                case 2 -> editStudentAccess();
                case 3 -> createCourse();
                case 4 -> updateCourse();
                case 5 -> checkIndexSlots();
                case 6 -> printByIndex();
                case 7 -> printByCourse();
                case 0 -> logOut();
                default -> System.out.println("Invalid option!");
            }
        }while(choice !=0);
    }
    private void logOut(){
        System.out.println("Log out successful!");
    }

    private void addStudent(){
        System.out.println("\n-> Add a new Student");
        String username, password, fullname, gender, nationality, dob, matric_number, matriculation_date, email;

        // username
        while (true){
            System.out.print("Enter username: ");
            username = sc.nextLine().toUpperCase();
            if (username.isEmpty())
                System.out.println("You need to enter something!");
            else if (data_container.existUsername(username))
                System.out.println("Username already exists!");
            else
                break;
        }

        // use console for password masking, tested to be working on window's cmd and mac's zsh shell
        // does not work on eclipse/intelliJ's integrated terminal
        Console cn = System.console();
        while (true){
            if (cn!=null)
                password = new String(cn.readPassword("PASSWORD: "));
            else {
                System.out.print("PASSWORD (unsupported terminal, password not masked): ");
                password = sc.nextLine();
            }
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
            nationality = sc.nextLine().toUpperCase();
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
                System.out.println("Matriculation number already exists, enter another!");
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

        // email
        while (true){
            System.out.print("Enter email address: ");
            email = sc.nextLine();

            // use regex to check if matric number entered matches format
            Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches())
                System.out.println("Invalid email!");
            else if(data_container.existEmail(email))
                System.out.println("Email already exists, enter another!");
            else
                break;
        }

        // call method in STARSPlanner to add student, print result
        System.out.println(staff_controller.addStudent(username, password, fullname, gender, nationality, dob,
                matric_number, matriculation_date, email));
    }

    private void editStudentAccess(){
        System.out.println("\n-> Edit student access period");
        String matric_number, startPeriod, endPeriod;

        // matric_number
        while (true){
            System.out.print("Enter student's matric number: ");
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
            System.out.print("Enter start of period (dd/mm/yyyy hh:mm): ");
            startPeriod = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:m");
                LocalDate.parse(startPeriod, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid date/time format, try again!");
            }
        }

        // end period
        while (true){
            System.out.print("Enter end of period (dd/mm/yyyy hh:mm am/pm): ");
            endPeriod = sc.nextLine();
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:m");
                LocalDate.parse(endPeriod, formatter); //use LocalDate.parse to check if date format is valid
                break;
            }catch(Exception e){
                System.out.println("Invalid date/time format, try again!");
            }
        }

        // set period, and print result
        System.out.println(staff_controller.setStudentAccess(matric_number, startPeriod, endPeriod));
    }

    private void createCourse(){
        System.out.println("\n-> Create Course");

        staff_controller.printAllCourses();

        String courseCode, courseName, courseSchool;
        int AU;

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
                System.out.println("School does not exist, try again!");
            else
                break;
        }

        while(true){
            try {
                System.out.print("Enter number of AUs: ");
                AU = Integer.parseInt(sc.nextLine());
                if (AU>0)
                    break;
                else
                    System.out.println("You need to enter a positive number!");
            }catch(Exception e){
                System.out.println("Invalid number!");
            }
        }
        System.out.println(staff_controller.createCourse(courseCode,courseName,courseSchool, AU));
    }

    private void updateCourse(){
        int choice=-1;
        do{
            System.out.println("\n[Update Course]");
            System.out.println("1. Update Course Code");
            System.out.println("2. Update Course Name");
            System.out.println("3. Create Index and Add to a Course");
            System.out.println("4. Remove Index from a Course");
            System.out.println("5. Add Lessons to an Index");
            System.out.println("6. Remove Lessons from an Index");
            System.out.println("0. Back");

            System.out.print("\nEnter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch(Exception e){
                System.out.println("Invalid input!");
                continue;
            }

            switch(choice){
                case 1 -> updateCourseCode();
                case 2 -> updateCourseName();
                case 3 -> createIndex();
                case 4 -> removeIndex();
                case 5 -> addLessons();
                case 6 -> removeLessons();
            }
        }while(choice !=0);
    }

    private void updateCourseCode(){
        staff_controller.printAllCourses();
        String oldCode, newCode;

        // course code
        oldCode = inputCourseCode();
        if (oldCode == null) return;

        // new code
        while (true){
            System.out.print("Enter New Course Code: ");
            newCode = sc.nextLine().toUpperCase();

            // use regex to check if course code entered matches format
            Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{4}$");
            Matcher matcher = pattern.matcher(newCode);

            if (!matcher.matches())
                System.out.println("Invalid course code!");
            else if(data_container.existCourse(newCode))
                System.out.println("That course code already exist, please enter another!");
            else
                break;
        }

        System.out.println(staff_controller.changeCourseCode(oldCode, newCode));

    }

    private void updateCourseName(){
        staff_controller.printAllCourses();
        String courseCode, newName;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // new course name
        while(true){
            System.out.print("Enter New Course Name: ");
            newName = sc.nextLine();
            if (newName.isEmpty())
                System.out.println("You need to enter something!");
            else
                break;
        }

        System.out.println(staff_controller.changeCourseName(courseCode, newName));
    }

    private void createIndex(){
        System.out.println("\n-> Create Index and Add to a Course");
        staff_controller.printAllCourses();
        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
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
            if (data_container.existIndexInCourse(courseCode, indexNumber)) {
                System.out.println("That index number already exist in the course!");
                return;
            }
            break;
        }

        int maxCapacity;
        while(true) {
            System.out.print("Enter max vacancies for this Index: ");

            // ensure user input is a valid index number in the right format
            try{
                maxCapacity = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.out.println("Invalid input!");
                continue;
            }

            if (maxCapacity <= 0)
                System.out.println("You need to enter a positive number, try again!");
            else
                break;
        }

        System.out.println(staff_controller.createIndex(courseCode,indexNumber,maxCapacity));
    }

    private void removeIndex(){
        System.out.println("\n-> Remove Index from a Course");
        staff_controller.printAllCourses();

        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        indexNumber = inputIndex(courseCode);
        if (indexNumber == -1) return;

        System.out.println(staff_controller.removeIndex(courseCode, indexNumber));
    }

    private void addLessons(){
        System.out.println("\n-> Add Lessons to an Index");
        staff_controller.printAllCourses();
        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        indexNumber = inputIndex(courseCode);
        if (indexNumber == -1) return;

        System.out.println("-----------All indexes-----------");
        staff_controller.printAlIndex(courseCode,indexNumber);

        // loop to add new timeslot
        while (true) {
            int lessonType, dayOfWeek;
            String location, startTime, endTime;

            // type of lesson
            while (true) {
                System.out.print("Enter type of lesson to add (1-Lecture, 2-Tutorial, 3-Lab): ");
                try{
                    lessonType = Integer.parseInt(sc.nextLine());
                }catch(Exception e){
                    System.out.println("Invalid input!");
                    continue;
                }

                if (lessonType == 1 || lessonType == 2 || lessonType == 3)
                    break;
                else
                    System.out.println("Invalid choice selected, try again!");
            }

            // day of week
            while (true) {
                System.out.print("Enter day of week (1-Monday, 2-Tuesday, etc): ");

                try{
                    dayOfWeek = Integer.parseInt(sc.nextLine());
                }catch(Exception e){
                    System.out.println("Invalid input!");
                    continue;
                }

                if (dayOfWeek >= 1 && dayOfWeek <= 7)
                    break;
                else
                    System.out.println("Invalid day of week, try again!");
            }

            // location
            while (true) {
                System.out.print("Enter lesson location: ");
                location = sc.nextLine();
                if (location.isEmpty())
                    System.out.println("You need to enter something!");
                else
                    break;
            }

            // start time
            LocalTime st;
            while (true){
                System.out.print("Enter start time (hh:mm am/pm): ");
                try{
                    startTime = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:m a");
                    st = LocalTime.parse(startTime, formatter);
                    if (st.getMinute()!=0 && st.getMinute()!= 30)
                        System.out.println("Time must be in 30-minute intervals");
                    else
                        break;
                }catch(Exception e){
                    System.out.println("Invalid time format, try again!");
                }
            }

            // end time
            LocalTime et;
            while (true){
                System.out.print("Enter end time (hh:mm am/pm): ");
                try{
                    endTime = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:m a");
                    et = LocalTime.parse(endTime, formatter);
                    if (st.getMinute()!=0 && st.getMinute()!= 30)
                        System.out.println("Time must be in 30-minute intervals");
                    else if (et.isBefore(st))
                        System.out.println("End time cannot be earlier than start time!");
                    else
                        break;
                }catch(Exception e){
                    System.out.println("Invalid time format, try again!");
                }
            }

            // add lesson
            System.out.println(staff_controller.addNewTimeSlot(courseCode, indexNumber, lessonType, dayOfWeek, location, startTime, endTime));

            System.out.println("-----------Summary-----------" + "\n" + "\n");
            staff_controller.printAlIndex(courseCode,indexNumber);

            // add another?
            System.out.print("Enter 'Y' to add another lesson: ");
            if (!sc.nextLine().toUpperCase().equals("Y"))
                break;
        }
    }

    private void removeLessons(){
        System.out.println("-> Remove Lesson from Index");
        staff_controller.printAllCourses();
        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        indexNumber = inputIndex(courseCode);
        if (indexNumber == -1) return;
        staff_controller.printAlIndex(courseCode,indexNumber);
        // call removeTimeSlot
        boolean isSuccessful = staff_controller.removeTimeSlot(courseCode,indexNumber);

        if (isSuccessful)
            staff_controller.printAlIndex(courseCode,indexNumber);
    }

    private void checkIndexSlots(){
        System.out.println("\n-> Check available slot for an index number");
        staff_controller.printAllCourses();
        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        indexNumber = inputIndex(courseCode);
        if (indexNumber == -1) return;

        System.out.println(staff_controller.checkIndexSlot(courseCode,indexNumber));
    }

    private void printByIndex() {
        System.out.println("\n-> Print Student list by index number");
        staff_controller.printAllCourses();

        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        indexNumber = inputIndex(courseCode);
        if (indexNumber == -1) return;

        System.out.println(staff_controller.printByIndex(courseCode, indexNumber));
    }

    private void printByCourse(){
        System.out.println("\n-> Print Student list by Course");
        staff_controller.printAllCourses();

        String courseCode;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        System.out.println(staff_controller.printByCourse(courseCode));
    }

//---------------------------- STUDENT MENU ----------------------------//
    private void studentMenu(String username){
        int choice=-1;
        do{
            System.out.println("----------------------------------------------------");
            System.out.printf("[Student Menu] Logged in as: %s\n", username);
            System.out.println("1. Register for a Course");
            System.out.println("2. Drop Registered Course");
            System.out.println("3. Drop Waitlisted Course");
            System.out.println("4. Display All Registered/Waitlisted Courses");
            System.out.println("5. Check Vacancies Available");
            System.out.println("6. Change Index Number of Registered Course");
            System.out.println("7. Swap Index Number with Another Student");
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
        student_controller.printAllCourses();

        String courseCode;
        int indexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        indexNumber = inputIndex(courseCode);
        if (indexNumber == -1) return;

        System.out.println(student_controller.registerForCourse(username, courseCode,indexNumber));
    }

    private void dropRegisteredCourse(String username){
        System.out.println("\n-> Drop a Registered Course");
        while (true) {
            if(student_controller.printRegisteredCourse(username) == null)
            {
                System.out.println("You are not registered in any courses.");
                break;
            }
            System.out.println("----------Registered Courses----------");
            System.out.println(student_controller.printRegisteredCourse(username));

            String courseCode;
            int indexNumber;

            // course code
            courseCode = inputCourseCode();
            if (courseCode == null) return;

            // index number
            indexNumber = inputIndex(courseCode);
            if (indexNumber == -1) return;

            System.out.println(student_controller.dropRegisteredCourse(username, courseCode, indexNumber));
            break;
        }

    }

    private void dropWaitlistedCourse(String username){
        System.out.println("\n-> Drop a Waitlisted Course");
        while(true) {
            if(student_controller.printWaitListedCourse(username) == null){
                System.out.println("You are not in wait list for any course!");
                break;
            }
            System.out.println("----------Wait Listed Courses----------");
            System.out.println(student_controller.printWaitListedCourse(username));
            String courseCode;
            int indexNumber;

            // course code
            courseCode = inputCourseCode();
            if (courseCode == null) return;

            // index number
            indexNumber = inputIndex(courseCode);
            if (indexNumber == -1) return;

            System.out.println(student_controller.dropWaitlistedCourse(username, courseCode, indexNumber));
        }
    }

    private void displayStudentCourse(String username){
        System.out.println("\n-> Registered/Waitlisted Courses");
        System.out.println(student_controller.displayStudentCourse(username));
    }

//    private void changeIndex(String username){
//        System.out.println("\n-> Change Index");
//        System.out.println("----------Registered Courses----------");
//        System.out.println(student_controller.printRegisteredCourse(username));
//
//
//        String courseCode;
//        int oldIndexNumber, newIndexNumber;
//
//        // course code
//        courseCode = inputCourseCode();
//        if (courseCode == null) return;
//
//        // old index number
//        oldIndexNumber = inputIndex(courseCode);
//        if (oldIndexNumber == -1) return;
//
//        // new index number
//
//        student_controller.printAllCourses();
//        while(true) {
//            System.out.print("Enter New Index Number: ");
//
//            // ensure user input is a valid index number in the right format
//            try{
//                newIndexNumber = Integer.parseInt(sc.nextLine());
//            }catch (Exception e){
//                System.out.println("Invalid index number!");
//                continue;
//            }
//
//            // check if index number exists in the course
//            if (oldIndexNumber == newIndexNumber) {
//                System.out.println("Old index cannot be the same as new index!");
//                return;
//            }else if (!data_container.existIndexInCourse(courseCode, newIndexNumber)) {
//                System.out.println("That index number does not exist in the course!");
//                return;
//            }
//            break;
//        }
//
//        System.out.println(student_controller.changeIndex(username, courseCode, oldIndexNumber, newIndexNumber));
//    }

    private void changeIndex(String username){
        System.out.println("\n-> Change Index");
        System.out.println("----------Registered Courses----------");

        String registeredCourses = student_controller.printRegisteredCourse(username);

        if (registeredCourses == null){
            System.out.println("No registered courses!");
            return;
        }
        else
            System.out.println(registeredCourses);

        String courseCode;
        int oldIndexNumber, newIndexNumber;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // old index number
        oldIndexNumber = inputIndex(courseCode);
        if (oldIndexNumber == -1) return;

        if (!student_controller.checkRegisteredIndex(username, courseCode, oldIndexNumber)){
            System.out.println("You are not registered in this course index!");
            return;
        }
        // new index number

        student_controller.printAllCourses();
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
            }else if (!data_container.existIndexInCourse(courseCode, newIndexNumber)) {
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

//        System.out.print("PASSWORD: ");
//        String password2 = sc.nextLine();

        Console cn = System.console();
        String password2;
        if (cn!=null)
            password2 = new String(cn.readPassword("PASSWORD: "));
        else {
            System.out.print("PASSWORD (unsupported terminal, password not masked): ");
            password2 = sc.nextLine();
        }

        if (username.equals(username2)) {
            System.out.println("You can't swap with yourself!");
            return;
        }

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

        String courseCode;

        // course code
        courseCode = inputCourseCode();
        if (courseCode == null) return;

        // index number
        int oldIndexNumber;
        oldIndexNumber = inputIndex(courseCode);
        if (oldIndexNumber == -1) return;

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
            }else if (!data_container.existIndexInCourse(courseCode, newIndexNumber)) {
                System.out.println("That index number does not exist in the course!");
                return;
            }
            break;
        }

        // try swap
        System.out.println(student_controller.swapIndex(username, username2,courseCode, oldIndexNumber,newIndexNumber ));
    }

    private String inputCourseCode(){
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
                System.out.println("Course code not found!");
                return null;
            }else
                return courseCode;
        }
    }

    private int inputIndex(String courseCode){
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
                return -1;
            }
            return indexNumber;
        }
    }


}
