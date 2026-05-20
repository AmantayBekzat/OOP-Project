package models;

import java.util.Date;

public class Message {
    private String content;
    private Date date;
    private User receiver;

    public Message() {
        this.date = new Date();
    }

    public Message(String content) {
        this.content = content;
        this.date = new Date();
    }

    public void send() {
        System.out.println("[" + date + "] " + content);
    }

    public User getReceiver() { return receiver; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
