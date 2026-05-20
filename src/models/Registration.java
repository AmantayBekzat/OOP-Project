package models;

import enums.RegistrationStatus;
import exceptions.CreditLimitExceededException;
import exceptions.RetakeLimitExceededException;

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
        if (enrolled + course.getCredits() > 21) throw new CreditLimitExceededException("Credit limit of 21 exceeded.");
        return true;
    }

    public boolean checkRetakeLimit() {
        int failures = 0;
        for (Mark mark : student.viewMark()) {
            if (!mark.isPassed()) failures++;
        }
        if (failures >= 3) throw new RetakeLimitExceededException("Retake limit of 3 exceeded.");
        return true;
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
