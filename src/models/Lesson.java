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

    public String getLessonInfo(){return null;}
}
