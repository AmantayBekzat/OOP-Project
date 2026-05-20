package models;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private String name;
    private String description;
    private List<User> subscribers;
    private List<ResearchPaper> papers;

    public Journal(String name, String description) {
        this.name = name;
        this.description = description;
        this.subscribers = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    public void subscribe(User user) {
        if (user == null || subscribers.contains(user)) return;
        subscribers.add(user);
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public void addPaper(ResearchPaper paper) {
        if (paper == null) return;
        papers.add(paper);
        notifySubscribers(paper);
    }

    public void notifySubscribers() {
        for (User user : subscribers) {
            System.out.println("Notification: New paper published in journal " + name);
        }
    }

    private void notifySubscribers(ResearchPaper paper) {
        for (User user : subscribers) {
            System.out.println("Notification: New paper published in journal " + name);
            System.out.println("  " + paper.getTitle());
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<User> getSubscribers() { return subscribers; }
    public List<ResearchPaper> getPapers() { return papers; }
}
