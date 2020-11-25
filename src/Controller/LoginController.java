package Controller;

import FileManager.DataContainer;
import Model.Staff;
import Model.Student;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * A controller that handles the login operations of this application.
 */
public class LoginController {
    private DataContainer dc;

    /**
     * The constructor.
     * @param dc The DataContainer object that holds the application's existing records.
     */
    public LoginController(DataContainer dc){
        this.dc = dc;
    }

    /**
     * Checks the validity of a username/password combination.
     * @param username The username to be authenticated.
     * @param password The password to be authenticated.
     * @return 1 if the username/password combination matches a staff record,
     *         2 if the username/password combination matches a student record within his/her allocated access period,
     *         3 if the username/password combination matches a student record outside his/her allocated access period,
     *         0 if the username/password does not match any record.
     */
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

    /**
     * Uses a hash function to translate a password into an hashed output for security reasons.
     * @param passwordStr The password string to be hashed.
     * @return The hashed representation of the password.
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
