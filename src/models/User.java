package models;
import enums.Language;

public abstract class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private Language language;

    public User(int id, String username, String password, String name, Language language){
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.language = language;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public Language getLanguage() { return language; }

    public void setPassword(String password) { this.password = password; }
    public void setLanguage(Language language) { this.language = language; }
    public void setName(String name) { this.name = name; }

    public boolean login(){
        return username != null && password != null;
    }
    public void logout(){
        System.out.println(name + " logged out.");
    }
    public void receiveNotification(Notification notification){
        notification.send();
    }
}
