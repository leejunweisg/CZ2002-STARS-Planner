package controllers;

import FileManager.DataContainer;
import model.Staff;
import model.Student;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Arrays;

public class LoginController {
    private DataContainer dc;

    public LoginController(DataContainer dc){
        this.dc = dc;
    }

    public int authenticate(String username, String password){

        for (Staff s : dc.getStaffList())
            if (s.getUsername().equals(username) && Arrays.equals(s.getPasswordHash(),hash(password)))
                return 1;

        for (Student stud : dc.getStudentList())
            if (stud.getUsername().equals(username) && Arrays.equals(stud.getPasswordHash(),hash(password)))
                if (LocalDateTime.now().isAfter(stud.getStartPeriod()) && LocalDateTime.now().isBefore(stud.getEndPeriod()))
                    return 2; // within access period
                else
                    return 3; // not in access period

        // no username/password pair found
        return 0;
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
