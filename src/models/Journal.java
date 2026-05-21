package models;

import interfaces.Observable;
import interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

public class Journal implements Observable {
    private String name;
    private String description;
    private List<Observer> subscribers;
    private List<ResearchPaper> papers;

    public Journal(String name, String description) {
        this.name = name;
        this.description = description;
        this.subscribers = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    @Override
    public void subscribe(Observer observer) {
        if (observer == null || subscribers.contains(observer)) return;
        subscribers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : subscribers) {
            observer.update(message);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (paper == null) return;
        papers.add(paper);
        notifyObservers("New paper in journal \"" + name + "\": " + paper.getTitle());
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Observer> getSubscribers() { return subscribers; }
    public List<ResearchPaper> getPapers() { return papers; }
}
