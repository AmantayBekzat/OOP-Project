package models;

import enums.CitationFormat;
import java.util.Date;

public class ResearchPaper {
    private String title;
    private String journal;
    private Date publicationDate;
    private int citations;
    private int pages;
    private String authors;

    public ResearchPaper() {}

    public ResearchPaper(String title, String journal, Date publicationDate, String authors, int pages) {
        this.title = title;
        this.journal = journal;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.pages = pages;
    }

    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.BIBTEX) {
            return "@article{" + title.replaceAll("\\s+", "") + ",\n"
                    + "  title={" + title + "},\n"
                    + "  author={" + authors + "},\n"
                    + "  journal={" + journal + "},\n"
                    + "  pages={" + pages + "},\n"
                    + "  year={" + (publicationDate != null ? publicationDate.getYear() + 1900 : "N/A") + "}\n"
                    + "}";
        }
        return authors + ". \"" + title + "\". " + journal + ", " + publicationDate + ". Pages: " + pages + ".";
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getJournal() { return journal; }
    public void setJournal(String journal) { this.journal = journal; }

    public Date getPublicationDate() { return publicationDate; }
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

    public int getCitations() { return citations; }
    public void setCitations(int citations) { this.citations = citations; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }
}
