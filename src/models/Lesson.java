package models;

import enums.LessonType;

import java.util.Date;

public class Lesson {
    private String topic;
    private LessonType lessonType;
    private Date date;

    public Lesson(String topic, LessonType lessonType, Date date){
        this.topic = topic;
        this.lessonType = lessonType;
        this.date = date;
    }

    public String getLessonInfo() {
        String t = (topic != null && !topic.isEmpty()) ? topic : "N/A";
        String lt = (lessonType != null) ? lessonType.name() : "N/A";
        String d = (date != null) ? String.valueOf(date) : "N/A";
        return "Topic: " + t + " | Type: " + lt + " | Date: " + d;
    }
}
