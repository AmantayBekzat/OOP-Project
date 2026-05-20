package models;

import enums.Language;

import java.util.Date;

public abstract class Employee extends User {
    private String employeeID;
    private double salary;
    private Date hireDate;

    public Employee(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate){
        super(id, username, password, name, language);
        this.employeeID = employeeID;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public void sendMessage(Message message){
        MessageService.getInstance().send(message.getReceiver(), message);
    }
}
