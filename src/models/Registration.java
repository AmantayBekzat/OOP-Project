package models;

import enums.RegistrationStatus;

public class Registration {
    private Student student;
    private Course course;
    private RegistrationStatus status;

    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.status = RegistrationStatus.PENDING;
    }

    public boolean checkCreditLimit() {
        int enrolled = 0;
        for (Course c : student.getCourses()) {
            enrolled += c.getCredits();
        }
        return enrolled + course.getCredits() <= 21;
    }

    public boolean checkRetakeLimit() {
        int failures = 0;
        for (Mark mark : student.viewMark()) {
            if (!mark.isPassed()) failures++;
        }
        return failures < 3;
    }

    public boolean checkPrerequisites() {
        return true;
    }

    public boolean validateCourseAvailability() {
        return !course.isFull() && !student.getCourses().contains(course);
    }

    public boolean isValid() {
        return checkCreditLimit() && checkRetakeLimit() && checkPrerequisites() && validateCourseAvailability();
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
}
