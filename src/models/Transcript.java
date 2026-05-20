package models;

import java.util.List;

public class Transcript {
    private Student student;

    public Transcript(Student student) {
        this.student = student;
    }

    public void generateTranscript() {
        System.out.println("=== Transcript ===");
        System.out.println("Student: " + student.getName());
        System.out.println("GPA: " + calculateGPA());
        List<Mark> marks = student.viewMark();
        if (marks.isEmpty()) {
            System.out.println("No marks recorded.");
            return;
        }
        for (Mark mark : marks) {
            System.out.println("  Total: " + mark.getTotalMark() + " | Grade: " + mark.getLetterGrade());
        }
    }

    public double calculateGPA() {
        List<Mark> marks = student.viewMark();
        if (marks.isEmpty()) return 0;
        double total = 0;
        for (Mark mark : marks) {
            total += mark.getPoints();
        }
        return total / marks.size();
    }
}
