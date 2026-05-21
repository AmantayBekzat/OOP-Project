package models;
import enums.Language;
import exceptions.AuthenticationException;
import interfaces.Observer;

public abstract class User implements Observer {
    private int id;
    private String username;
    private String password;
    private String name;
    private Language language;
    private boolean loggedIn;

    public User(int id, String username, String password, String name, Language language) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.language = language;
        this.loggedIn = false;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public Language getLanguage() { return language; }

    public void setPassword(String password) { this.password = password; }
    public void setLanguage(Language language) { this.language = language; }
    public void setName(String name) { this.name = name; }

    public boolean login() {
        if (username == null || username.isEmpty()) throw new AuthenticationException("Username is missing.");
        if (password == null || password.isEmpty()) throw new AuthenticationException("Password is missing.");
        loggedIn = true;
        return true;
    }

    public void logout() {
        loggedIn = false;
        System.out.println(name + " logged out.");
    }

    public boolean isAuthenticated() {
        return loggedIn;
    }

    public void receiveNotification(Notification notification) {
        notification.send();
    }

    @Override
    public void update(String message) {
        receiveNotification(new Notification(message));
    }
}
