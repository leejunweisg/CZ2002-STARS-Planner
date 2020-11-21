package Notification;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email implements NotificationInterface{

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
            System.out.println("Email Notification sent!");

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
