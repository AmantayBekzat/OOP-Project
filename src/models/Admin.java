package models;

import enums.Language;

import java.util.Date;
import java.util.List;
import models.LogRecord;

public class Admin extends Employee{

    public Admin(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate){
        super(id, username, password, name, language, employeeID, salary, hireDate);
    }

    public void addUser(User user){}
    public void removeUser(User user){}
    public void updateUser(User user){}
    public List<LogRecord> viewLogs(){return null;}

}
