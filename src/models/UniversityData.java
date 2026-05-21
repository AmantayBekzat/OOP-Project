package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversityData {
    private static UniversityData instance;

    private final List<User>             users       = new ArrayList<>();
    private final List<Course>           courses     = new ArrayList<>();
    private final List<News>             news        = new ArrayList<>();
    private final List<Complaint>        complaints  = new ArrayList<>();
    private final Map<String, String>    credentials = new HashMap<>();

    private UniversityData() {}

    public static UniversityData getInstance() {
        if (instance == null) instance = new UniversityData();
        return instance;
    }

    public List<User>          getUsers()       { return users; }
    public List<Course>        getCourses()     { return courses; }
    public List<News>          getNews()        { return news; }
    public List<Complaint>     getComplaints()  { return complaints; }
    public Map<String, String> getCredentials() { return credentials; }
}
