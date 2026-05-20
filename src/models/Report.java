package models;

import java.util.Date;

public class Report {
    private String title;
    private Date generatedDate;

    public Report() {}

    public Report(String title) {
        this.title = title;
    }

    public void generate() {
        this.generatedDate = new Date();
        System.out.println("Report generated: " + title + " at " + generatedDate);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(Date generatedDate) { this.generatedDate = generatedDate; }
}
