package models;
import java.util.List;

public class Journal {
    private String name;
    private String description;
    private List<User> subscribers;
    private List<ResearchPaper> papers;

    public void subscribe(User user){}
    public void unsubscribe(User user){}
    public void addPaper(ResearchPaper paper){}
    public void notifySubscribers(){}
}