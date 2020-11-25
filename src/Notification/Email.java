package notification;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * The Email class that handles the sending of notifications via email.
 */
public class Email implements NotificationInterface{

    /**
     * Sends a notification via email.
     * @param title The title of the email.
     * @param body The body of the email.
     * @param email The recipient email address.
     * @return Returns true if successfully sent, else false.
     */
    @Override
    public boolean sendNotification(String title, String body, String email){
        final String username = "donotreply.starsntu@gmail.com";
        final String password = "1q2w3e4r5t6y!";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(title);
            message.setText(body);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
