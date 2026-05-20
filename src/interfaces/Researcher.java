package interfaces;

import models.ResearchPaper;

import java.util.Comparator;

public interface Researcher {
     void publishPaper();
     void joinResearchProject();
     int calculateHIndex();
     void printPapers(Comparator<ResearchPaper> c);
}

