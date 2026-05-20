package comparators;
import models.ResearchPaper;
import java.util.Comparator;

public class PageCountComparator implements Comparator<ResearchPaper> {
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        if (p1 == null && p2 == null) return 0;
        if (p1 == null) return 1;
        if (p2 == null) return -1;
        return Integer.compare(p2.getPages(), p1.getPages());
    }
}