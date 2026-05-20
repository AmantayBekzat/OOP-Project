package models;

import enums.DegreeType;
import enums.Language;
import interfaces.Researcher;

import java.util.Comparator;

public class GraduateStudent extends Student implements Researcher {
    private DegreeType degreeType;
    private String thesisTitle;
    private Researcher supervisor;

    public GraduateStudent(int id, String username, String password, String name, Language language, String studentId, int yearOfStudy, double gpa, int totalCredits, DegreeType degreeType, String thesisTitle, Researcher supervisor){
        super(id, username, password, name, language, studentId, yearOfStudy, gpa, totalCredits);
        this.degreeType = degreeType;
        this.thesisTitle = thesisTitle;
        this.supervisor = supervisor;
    }

    public void submitDiplomaProject(){}
    public Researcher viewSupervisor(){return null;}
    public void publishPaper(){}
    public void joinResearchProject(){}
    public int calculateHIndex(){return 1;}
    public void printPapers(Comparator<ResearchPaper> c){}

}
