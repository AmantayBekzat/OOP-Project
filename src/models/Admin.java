package models;

import enums.Language;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin extends Employee {

    private static final List<User> users = new ArrayList<>();
    private static final List<LogRecord> logs = new ArrayList<>();

    public Admin(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate) {
        super(id, username, password, name, language, employeeID, salary, hireDate);
    }

    public void addUser(User user) {
        if (user == null || users.contains(user)) return;
        users.add(user);
        logs.add(new LogRecord("ADD_USER: " + user.getUsername(), this));
    }

    public void removeUser(User user) {
        if (user == null || !users.contains(user)) return;
        users.remove(user);
        logs.add(new LogRecord("REMOVE_USER: " + user.getUsername(), this));
    }

    public void updateUser(User user) {
        if (user == null || !users.contains(user)) return;
        logs.add(new LogRecord("UPDATE_USER: " + user.getUsername(), this));
    }

    public List<LogRecord> viewLogs() {
        return logs;
    }

    public static List<User> getUsers() { return users; }
}
