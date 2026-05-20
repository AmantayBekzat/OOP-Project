package models;

import enums.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends User {
    private String studentId;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private List<Course> courses;
    private List<Mark> marks;

    public Student(int id, String username, String password, String name, Language language, String studentId, int yearOfStudy, double gpa, int totalCredits) {
        super(id, username, password, name, language);
        this.studentId = studentId;
        this.yearOfStudy = yearOfStudy;
        this.gpa = gpa;
        this.totalCredits = totalCredits;
        this.courses = new ArrayList<>();
        this.marks = new ArrayList<>();
    }

    public void registerForCourse(Course course) {
        if (course == null || courses.contains(course)) return;
        course.addStudent(this);
        courses.add(course);
    }

    public List<Mark> viewMark() {
        return Collections.unmodifiableList(marks);
    }

    void addMark(Mark mark) {
        if (mark != null) marks.add(mark);
    }

    public Transcript viewTranscript() {
        return new Transcript(this);
    }

    public void rateTeacher(Teacher teacher, int rate) {
        System.out.println(getName() + " rated " + teacher.getName() + ": " + rate + "/5");
    }

    public String getStudentId() { return studentId; }
    public int getYearOfStudy() { return yearOfStudy; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public int getTotalCredits() { return totalCredits; }
    public void setTotalCredits(int totalCredits) { this.totalCredits = totalCredits; }
    public List<Course> getCourses() { return courses; }
}
