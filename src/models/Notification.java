package models;

import java.util.Date;

public class Notification {
    private String message;
    private Date date;
    private boolean isRead;

    public Notification(String message) {
        this.message = message;
        this.date = new Date();
        this.isRead = false;
    }

    public Notification(String message, boolean isRead) {
        this.message = message;
        this.date = new Date();
        this.isRead = isRead;
    }

    public void send() {
        System.out.println("[NOTIFICATION] " + message + " | status: " + (isRead ? "read" : "unread"));
    }

    public void markAsRead() {
        isRead = true;
    }

    public boolean isRead() { return isRead; }
}
