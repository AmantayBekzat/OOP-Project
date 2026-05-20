package models;

import enums.DegreeType;
import enums.Language;
import exceptions.InvalidSupervisorException;
import interfaces.Researcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GraduateStudent extends Student implements Researcher {
    private DegreeType degreeType;
    private String thesisTitle;
    private Researcher supervisor;
    private List<ResearchPaper> papers;

    public GraduateStudent(int id, String username, String password, String name, Language language, String studentId, int yearOfStudy, double gpa, int totalCredits, DegreeType degreeType, String thesisTitle, Researcher supervisor) {
        super(id, username, password, name, language, studentId, yearOfStudy, gpa, totalCredits);
        if (supervisor == null) throw new InvalidSupervisorException("Supervisor cannot be null");
        if (supervisor.calculateHIndex() < 3) throw new InvalidSupervisorException("Supervisor h-index too low");
        this.degreeType = degreeType;
        this.thesisTitle = thesisTitle;
        this.supervisor = supervisor;
        this.papers = new ArrayList<>();
    }

    public void submitDiplomaProject() {
        System.out.println("Diploma project submitted: " + thesisTitle);
        ResearchPaper thesis = new ResearchPaper(thesisTitle, "University Press", new Date(), getName(), 0);
        papers.add(thesis);
    }

    public Researcher viewSupervisor() {
        return supervisor;
    }

    public void publishPaper() {
        System.out.println("Graduate student published research paper");
    }

    public void joinResearchProject() {
        System.out.println("Joined research project");
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
            System.out.println(p.getTitle() + " | Citations: " + p.getCitations() + " | Journal: " + p.getJournal() + " | Date: " + p.getPublicationDate());
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null) papers.add(paper);
    }

    public List<ResearchPaper> getPapers() { return papers; }
    public DegreeType getDegreeType() { return degreeType; }
    public String getThesisTitle() { return thesisTitle; }
}
