package models;

import java.util.Date;

public class LogRecord {
    private String action;
    private Date date;
    private User user;

    public LogRecord() {
        this.date = new Date();
    }

    public LogRecord(String action, User user) {
        this.action = action;
        this.user = user;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "[" + date + "] " + user.getUsername() + " -> " + action;
    }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
