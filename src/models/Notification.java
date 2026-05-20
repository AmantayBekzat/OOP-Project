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

    public void send(){
        System.out.println(message);
    }
    public void markAsRead(){
        isRead = true;
    }
}
