package models;

import enums.Language;
import enums.ManagerType;

import java.util.Date;
import java.util.List;

public class Manager extends Employee{
    private ManagerType managerType;

    public Manager(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate, ManagerType managerType){
        super(id, username, password, name, language, employeeID, salary, hireDate);
        this.managerType = managerType;
    }

    public void assignCourse(Course course, Teacher teacher){}
    public void approveRegistration(Registration registration){}
    public Report createReport(){return null;}
    public void manageNews(News news){}
    public List<Complaint> viewComplaints(){return null;}
    public void processComplaint(Complaint complaint){}
}
