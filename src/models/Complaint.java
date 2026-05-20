package models;

import enums.ComplaintStatus;
import enums.UrgencyLevel;
import models.Student;
import models.Teacher;

public class Complaint {
    private Teacher teacher;
    private Student student;
    private String text;
    private UrgencyLevel urgency;
    private ComplaintStatus status;

    public Complaint() {}

    public Complaint(Teacher teacher, Student student, String text, UrgencyLevel urgency) {
        this.teacher = teacher;
        this.student = student;
        this.text = text;
        this.urgency = urgency;
        this.status = ComplaintStatus.NEW;
    }

    public void submit() {
        this.status = ComplaintStatus.NEW;
    }

    public void process() {
        this.status = ComplaintStatus.IN_PROGRESS;
    }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public UrgencyLevel getUrgency() { return urgency; }
    public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }

    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) { this.status = status; }
}
