package models;

import exceptions.NotResearcherException;
import interfaces.Researcher;

import java.util.ArrayList;
import java.util.List;

public class ResearchProject {
    private String title;
    private String description;
    private List<Researcher> participants = new ArrayList<>();

    public ResearchProject() {}

    public ResearchProject(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void addParticipant(Researcher researcher) throws NotResearcherException {
        if (researcher == null) {
            throw new NotResearcherException("Participant must be a Researcher.");
        }
        participants.add(researcher);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Researcher> getParticipants() { return participants; }
    public void setParticipants(List<Researcher> participants) { this.participants = participants; }
}
