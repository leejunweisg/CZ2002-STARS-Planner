package Notification;

/**
 * The Notification Interface that defines the methods for each Notification medium class that implements this interface.
 */
public interface NotificationInterface {

    /**
     * Abstract method that defines the parameters for sending a notification
     * @param title The title of the notification.
     * @param message The message of the notification.
     * @param recipient The recipient.
     * @return Returns true if notification sent successfully, else false.
     */
    public boolean sendNotification(String title, String message, String recipient);
}
