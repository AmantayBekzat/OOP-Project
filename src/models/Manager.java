package models;

import enums.Language;
import enums.ManagerType;
import enums.RegistrationStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager extends Employee {
    private ManagerType managerType;
    private List<Complaint> complaints;
    private List<News> newsList;

    public Manager(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate, ManagerType managerType) {
        super(id, username, password, name, language, employeeID, salary, hireDate);
        this.managerType = managerType;
        this.complaints = new ArrayList<>();
        this.newsList = new ArrayList<>();
    }

    public void assignCourse(Course course, Teacher teacher) {
        if (course == null || teacher == null) return;
        teacher.manageCourse(course);
    }

    public void approveRegistration(Registration registration) {
        if (registration == null) return;
        if (registration.isValid()) {
            registration.getStudent().registerForCourse(registration.getCourse());
            registration.setStatus(RegistrationStatus.APPROVED);
        } else {
            registration.setStatus(RegistrationStatus.REJECTED);
        }
    }

    public Report createReport() {
        Report report = new Report("Academic Report - " + getName());
        report.generate();
        return report;
    }

    public void manageNews(News news) {
        if (news == null) return;
        newsList.add(news);
        news.publish();
    }

    public List<Complaint> viewComplaints() {
        return complaints;
    }

    public void processComplaint(Complaint complaint) {
        if (complaint == null) return;
        complaint.process();
        if (!complaints.contains(complaint)) complaints.add(complaint);
    }

    public ManagerType getManagerType() { return managerType; }
    public List<News> getNewsList() { return newsList; }
}
