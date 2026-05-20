package comparators;
import models.ResearchPaper;
import java.util.Comparator;

public class DateComparator implements Comparator<ResearchPaper> {
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        if (p1 == null && p2 == null) return 0;
        if (p1 == null) return 1;
        if (p2 == null) return -1;
        if (p1.getPublicationDate() == null && p2.getPublicationDate() == null) return 0;
        if (p1.getPublicationDate() == null) return 1;
        if (p2.getPublicationDate() == null) return -1;
        return p2.getPublicationDate().compareTo(p1.getPublicationDate());
    }
}