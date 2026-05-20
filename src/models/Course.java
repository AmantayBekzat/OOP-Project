package models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private int capacity;
    private List<Student> enrolledStudents;
    private List<Lesson> lessons;

    public Course(String courseCode, String title, int credits, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student == null) return;
        if (enrolledStudents.size() >= capacity) return;
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null) lessons.add(lesson);
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public int getEnrolledCount() {
        return enrolledStudents.size();
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Student> getEnrolledStudents() { return enrolledStudents; }

    public List<Lesson> getLessons() { return lessons; }
}
