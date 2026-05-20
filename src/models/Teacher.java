package models;

import enums.Language;
import enums.TeacherType;
import enums.UrgencyLevel;
import interfaces.Researcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Teacher extends Employee implements Researcher {
    private TeacherType teacherType;
    private List<Course> courses;
    private List<ResearchPaper> papers;
    private List<Complaint> complaints;

    public Teacher(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate, TeacherType teacherType) {
        super(id, username, password, name, language, employeeID, salary, hireDate);
        this.teacherType = teacherType;
        this.courses = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.complaints = new ArrayList<>();
    }

    public void manageCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (student == null || course == null || mark == null) return;
        if (!student.getCourses().contains(course)) return;
        student.addMark(mark);
    }

    public void sendComplaint(Student student, String text, UrgencyLevel urgency) {
        Complaint complaint = new Complaint(this, student, text, urgency);
        complaint.submit();
        complaints.add(complaint);
    }

    public void publishPaper() {
        System.out.println(getName() + " published a research paper.");
    }

    public void joinResearchProject() {
        System.out.println(getName() + " joined a research project.");
    }

    public int calculateHIndex() {
        if (papers == null || papers.isEmpty()) return 0;
        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper p : papers) {
            if (p != null) citations.add(p.getCitations());
        }
        if (citations.isEmpty()) return 0;
        citations.sort((a, b) -> b - a);
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }

    public void printPapers(Comparator<ResearchPaper> c) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort(c);
        for (ResearchPaper p : sorted) {
            System.out.println(p.getTitle() + " | Citations: " + p.getCitations() + " | Pages: " + p.getPages());
        }
    }

    public TeacherType getTeacherType() { return teacherType; }
    public List<Course> getCourses() { return courses; }
    public List<ResearchPaper> getPapers() { return papers; }
    public void addPaper(ResearchPaper paper) { if (paper != null) papers.add(paper); }
    public List<Complaint> getComplaints() { return complaints; }
}
