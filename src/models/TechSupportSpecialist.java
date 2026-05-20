package models;

import enums.Language;

import java.util.Date;
import java.util.List;

public class TechSupportSpecialist extends Employee {
    public TechSupportSpecialist(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate){
        super(id, username, password, name, language, employeeID, salary, hireDate);
    }

    public List<Request> viewRequests(){ return  null; }
    public void acceptRequest(Request request){}
    public void rejectRequest(Request request){}
}
