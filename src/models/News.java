package models;

import java.util.Date;

public class News {
    private String title;
    private String content;
    private Date date;

    public News() {
        this.date = new Date();
    }

    public News(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = new Date();
    }

    public void publish() {
        System.out.println("News published: " + title + "\n" + content);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
