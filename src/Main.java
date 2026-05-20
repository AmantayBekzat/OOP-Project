import comparators.CitationComparator;
import comparators.DateComparator;
import comparators.PageCountComparator;
import enums.*;
import exceptions.CreditLimitExceededException;
import exceptions.RetakeLimitExceededException;
import interfaces.Researcher;
import models.*;

import java.util.*;

public class Main {

    static final List<User> allUsers = new ArrayList<>();
    static final List<Course> allCourses = new ArrayList<>();
    static final List<News> allNews = new ArrayList<>();
    static final List<Complaint> allComplaints = new ArrayList<>();
    static final Map<String, String> credentials = new HashMap<>();
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        setupSystem();
        while (true) {
            User user = loginScreen();
            if (user != null) showMenu(user);
        }
    }

    static void setupSystem() {
        Admin admin = new Admin(1, "admin", "admin", "Admin", Language.EN, "EMP001", 150000, new Date());
        Manager manager = new Manager(2, "manager", "manager", "Manager", Language.EN, "EMP002", 120000, new Date(), ManagerType.OR);
        Teacher teacher = new Teacher(3, "teacher", "teacher", "Teacher", Language.EN, "EMP003", 100000, new Date(), TeacherType.PROFESSOR);
        Student student = new Student(4, "student", "student", "Student", Language.EN, "STU001", 2, 3.5, 0);
        TechSupportSpecialist tech = new TechSupportSpecialist(5, "tech", "tech", "Tech", Language.EN, "EMP004", 80000, new Date());

        credentials.put("admin", "admin");
        credentials.put("manager", "manager");
        credentials.put("teacher", "teacher");
        credentials.put("student", "student");
        credentials.put("tech", "tech");

        allUsers.addAll(Arrays.asList(admin, manager, teacher, student, tech));
        admin.addUser(manager);
        admin.addUser(teacher);
        admin.addUser(student);
        admin.addUser(tech);

        ResearchPaper p1 = new ResearchPaper("AI in Education", "IEEE", new Date(), "Teacher", 10);
        p1.setCitations(10);
        ResearchPaper p2 = new ResearchPaper("ML Algorithms", "ACM", new Date(), "Teacher", 8);
        p2.setCitations(8);
        ResearchPaper p3 = new ResearchPaper("Deep Learning", "Nature", new Date(), "Teacher", 5);
        p3.setCitations(5);
        teacher.addPaper(p1);
        teacher.addPaper(p2);
        teacher.addPaper(p3);

        GraduateStudent graduate = new GraduateStudent(6, "graduate", "graduate", "Graduate", Language.EN, "STU002", 1, 0.0, 0, DegreeType.MASTER, "AI Research", teacher);
        credentials.put("graduate", "graduate");
        allUsers.add(graduate);
        admin.addUser(graduate);

        Course math = new Course("MATH101", "Calculus I", 6, 30);
        Course cs = new Course("CS101", "Intro to CS", 5, 25);
        Course physics = new Course("PHYS101", "Physics I", 4, 20);
        teacher.manageCourse(math);
        teacher.manageCourse(cs);
        allCourses.addAll(Arrays.asList(math, cs, physics));

        Request req1 = new Request("Projector broken in Room 101");
        Request req2 = new Request("Network issue in Lab 3");
        tech.addRequest(req1);
        tech.addRequest(req2);

        allNews.add(new News("Welcome to Spring Semester", "Classes begin February 1."));
    }

    // ===================== LOGIN =====================
    static User loginScreen() {
        System.out.println("\n========================================");
        System.out.println("         UNIVERSITY SYSTEM LOGIN");
        System.out.println("========================================");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User found = null;
        for (User u : allUsers) {
            if (u.getUsername().equals(username)) { found = u; break; }
        }

        if (found == null) { System.out.println("[ERROR] User not found."); return null; }
        if (!credentials.getOrDefault(username, "").equals(password)) { System.out.println("[ERROR] Incorrect password."); return null; }

        try {
            found.login();
            System.out.println("Welcome, " + found.getName() + "!");
            return found;
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    static void showMenu(User user) {
        if      (user instanceof Admin)                 adminMenu((Admin) user);
        else if (user instanceof Manager)               managerMenu((Manager) user);
        else if (user instanceof Teacher)               teacherMenu((Teacher) user);
        else if (user instanceof GraduateStudent)       studentMenu((GraduateStudent) user);
        else if (user instanceof Student)               studentMenu((Student) user);
        else if (user instanceof TechSupportSpecialist) techMenu((TechSupportSpecialist) user);
    }

    // ===================== ADMIN MENU =====================
    static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add user");
            System.out.println("2. Remove user");
            System.out.println("3. View logs");
            System.out.println("4. View news");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    System.out.print("Username: ");  String uname = scanner.nextLine().trim();
                    System.out.print("Password: ");  String pwd   = scanner.nextLine().trim();
                    System.out.print("Full name: "); String name  = scanner.nextLine().trim();
                    Student ns = new Student(allUsers.size() + 1, uname, pwd, name, Language.EN, "STU" + allUsers.size(), 1, 0.0, 0);
                    admin.addUser(ns);
                    allUsers.add(ns);
                    credentials.put(uname, pwd);
                    System.out.println("User added: " + name);
                    break;
                }
                case "2": {
                    if (allUsers.isEmpty()) { System.out.println("No users."); break; }
                    for (int i = 0; i < allUsers.size(); i++)
                        System.out.println((i + 1) + ". " + allUsers.get(i).getName() + " [" + allUsers.get(i).getUsername() + "]");
                    System.out.print("Select user to remove: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < allUsers.size()) {
                            User u = allUsers.get(idx);
                            admin.removeUser(u);
                            allUsers.remove(u);
                            System.out.println("Removed: " + u.getName());
                        } else System.out.println("Invalid selection.");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "3": {
                    List<LogRecord> logs = admin.viewLogs();
                    if (logs.isEmpty()) System.out.println("No logs.");
                    else logs.forEach(System.out::println);
                    break;
                }
                case "4":
                    if (allNews.isEmpty()) System.out.println("No news available.");
                    else allNews.forEach(n -> System.out.println("[" + n.getDate() + "] " + n.getTitle() + ": " + n.getContent()));
                    break;
                case "5":
                    admin.logout();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ===================== STUDENT MENU =====================
    static void studentMenu(Student student) {
        boolean isResearcher = student instanceof Researcher;
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. Register for course");
            System.out.println("2. View marks");
            System.out.println("3. View transcript");
            System.out.println("4. View news");
            System.out.println("5. Rate teacher");
            System.out.println("6. View teacher info");
            if (isResearcher) System.out.println("7. Research Menu");
            System.out.println(isResearcher ? "8. Logout" : "7. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    System.out.println("Available courses:");
                    for (int i = 0; i < allCourses.size(); i++) {
                        Course c = allCourses.get(i);
                        System.out.println((i + 1) + ". " + c.getTitle() + " (" + c.getCourseCode() + ") | Credits: " + c.getCredits() + " | Spots left: " + (c.getCapacity() - c.getEnrolledCount()));
                    }
                    System.out.print("Select course: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= allCourses.size()) { System.out.println("Invalid."); break; }
                        Course selected = allCourses.get(idx);
                        Registration reg = new Registration(student, selected);
                        try {
                            if (reg.isValid()) {
                                student.registerForCourse(selected);
                                System.out.println("Enrolled in: " + selected.getTitle());
                            } else {
                                System.out.println("Cannot register: course full or already enrolled.");
                            }
                        } catch (CreditLimitExceededException | RetakeLimitExceededException e) {
                            System.out.println("[ERROR] " + e.getMessage());
                        }
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "2": {
                    List<Mark> marks = student.viewMark();
                    if (marks.isEmpty()) System.out.println("No marks yet.");
                    else marks.forEach(m -> System.out.println("Total: " + m.getTotalMark() + " | Grade: " + m.getLetterGrade() + " | Passed: " + m.isPassed()));
                    break;
                }
                case "3":
                    student.viewTranscript().generateTranscript();
                    break;
                case "4":
                    if (allNews.isEmpty()) System.out.println("No news available.");
                    else allNews.forEach(n -> System.out.println("[" + n.getDate() + "] " + n.getTitle() + ": " + n.getContent()));
                    break;
                case "5": {
                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Teacher) teachers.add((Teacher) u);
                    if (teachers.isEmpty()) { System.out.println("No teachers available."); break; }
                    for (int i = 0; i < teachers.size(); i++)
                        System.out.println((i + 1) + ". " + teachers.get(i).getName());
                    System.out.print("Select teacher: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= teachers.size()) { System.out.println("Invalid."); break; }
                        System.out.print("Rating (1-5): ");
                        int rate = Integer.parseInt(scanner.nextLine().trim());
                        if (rate < 1 || rate > 5) { System.out.println("Rating must be between 1 and 5."); break; }
                        student.rateTeacher(teachers.get(idx), rate);
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "6": {
                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Teacher) teachers.add((Teacher) u);
                    if (teachers.isEmpty()) { System.out.println("No teachers available."); break; }
                    for (int i = 0; i < teachers.size(); i++)
                        System.out.println((i + 1) + ". " + teachers.get(i).getName());
                    System.out.print("Select teacher: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= teachers.size()) { System.out.println("Invalid."); break; }
                        Teacher t = teachers.get(idx);
                        System.out.println("--- Teacher Info ---");
                        System.out.println("Name:        " + t.getName());
                        System.out.println("Type:        " + t.getTeacherType());
                        System.out.println("Courses:     " + t.getCourses().size());
                        System.out.println("Papers:      " + t.getPapers().size());
                        System.out.println("H-Index:     " + t.calculateHIndex());
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "7":
                    if (isResearcher) { researchMenu((Researcher) student); break; }
                    student.logout();
                    return;
                case "8":
                    if (isResearcher) { student.logout(); return; }
                    System.out.println("Invalid choice.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ===================== TEACHER MENU =====================
    static void teacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("\n=== TEACHER MENU ===");
            System.out.println("1. Manage course");
            System.out.println("2. Put mark");
            System.out.println("3. Send message");
            System.out.println("4. Send complaint");
            System.out.println("5. Research Menu");
            System.out.println("6. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    List<Course> courses = teacher.getCourses();
                    if (courses.isEmpty()) { System.out.println("No courses assigned."); break; }
                    courses.forEach(c -> {
                        System.out.println("  [" + c.getCourseCode() + "] " + c.getTitle() + " | Enrolled: " + c.getEnrolledCount() + "/" + c.getCapacity());
                        c.getLessons().forEach(l -> System.out.println("    " + l.getLessonInfo()));
                    });
                    break;
                }
                case "2": {
                    List<Course> courses = teacher.getCourses();
                    if (courses.isEmpty()) { System.out.println("No courses assigned."); break; }
                    List<Student> enrolled = new ArrayList<>();
                    for (Course c : courses)
                        for (Student s : c.getEnrolledStudents())
                            if (!enrolled.contains(s)) enrolled.add(s);
                    if (enrolled.isEmpty()) { System.out.println("No enrolled students."); break; }
                    for (int i = 0; i < enrolled.size(); i++)
                        System.out.println((i + 1) + ". " + enrolled.get(i).getName());
                    System.out.print("Select student: ");
                    try {
                        int sIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (sIdx < 0 || sIdx >= enrolled.size()) { System.out.println("Invalid."); break; }
                        for (int i = 0; i < courses.size(); i++)
                            System.out.println((i + 1) + ". " + courses.get(i).getTitle());
                        System.out.print("Select course: ");
                        int cIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (cIdx < 0 || cIdx >= courses.size()) { System.out.println("Invalid."); break; }
                        System.out.print("First attestation: ");
                        double fa = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Second attestation: ");
                        double sa = Double.parseDouble(scanner.nextLine().trim());
                        if (fa + sa > 60) { System.out.println("Maximum f+s attestation: 60"); break; }
                        if (fa + sa < 30) {
                            Mark mark = new Mark(fa, sa, 0);
                            teacher.putMark(enrolled.get(sIdx), courses.get(cIdx), mark);
                            System.out.println("Attestation total < 30. Student did not pass to final. Grade: F");
                            break;
                        }
                        System.out.print("Final exam: ");
                        double fe = Double.parseDouble(scanner.nextLine().trim());
                        if (fe > 40) { System.out.println("Maximum: 40"); break; }
                        if (fa + sa + fe > 100) { System.out.println("Maximum: 100"); break; }
                        Mark mark = new Mark(fa, sa, fe);
                        teacher.putMark(enrolled.get(sIdx), courses.get(cIdx), mark);
                        System.out.println("Mark assigned: " + mark.getTotalMark() + " (" + mark.getLetterGrade() + ")");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "3": {
                    for (int i = 0; i < allUsers.size(); i++)
                        System.out.println((i + 1) + ". " + allUsers.get(i).getName());
                    System.out.print("Select recipient: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= allUsers.size()) { System.out.println("Invalid."); break; }
                        User recipient = allUsers.get(idx);
                        System.out.print("Message: ");
                        String content = scanner.nextLine().trim();
                        teacher.sendMessage(new Message(content, recipient));
                        System.out.println("Message sent to " + recipient.getName() + ".");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "4": {
                    List<Student> students = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Student) students.add((Student) u);
                    if (students.isEmpty()) { System.out.println("No students."); break; }
                    for (int i = 0; i < students.size(); i++)
                        System.out.println((i + 1) + ". " + students.get(i).getName());
                    System.out.print("Select student: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= students.size()) { System.out.println("Invalid."); break; }
                        System.out.print("Complaint text: ");
                        String text = scanner.nextLine().trim();
                        System.out.println("Urgency: 1. LOW  2. MEDIUM  3. HIGH");
                        System.out.print("Choice: ");
                        int u = Integer.parseInt(scanner.nextLine().trim());
                        UrgencyLevel urgency = u == 1 ? UrgencyLevel.LOW : u == 3 ? UrgencyLevel.HIGH : UrgencyLevel.MEDIUM;
                        teacher.sendComplaint(students.get(idx), text, urgency);
                        Complaint c = teacher.getComplaints().get(teacher.getComplaints().size() - 1);
                        allComplaints.add(c);
                        System.out.println("Complaint sent. Status: " + c.getStatus() + " | Urgency: " + urgency);
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "5":
                    researchMenu(teacher);
                    break;
                case "6":
                    teacher.logout();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ===================== RESEARCH MENU =====================
    static void researchMenu(Researcher researcher) {
        while (true) {
            System.out.println("\n=== RESEARCH MENU ===");
            System.out.println("1. Publish Paper");
            System.out.println("2. View My Papers");
            System.out.println("3. Calculate H-Index");
            System.out.println("4. Print Papers (sorted)");
            System.out.println("5. Join Research Project");
            System.out.println("6. Simulate Citation");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    String authorName = (researcher instanceof User) ? ((User) researcher).getName() : "Unknown";
                    try {
                        System.out.print("Title: ");   String title   = scanner.nextLine().trim();
                        System.out.print("Journal: "); String journal = scanner.nextLine().trim();
                        System.out.print("Pages: ");   int pages = Integer.parseInt(scanner.nextLine().trim());
                        ResearchPaper paper = new ResearchPaper(title, journal, new Date(), authorName, pages);
                        paper.setCitations(0);
                        if (researcher instanceof Teacher) ((Teacher) researcher).addPaper(paper);
                        else if (researcher instanceof GraduateStudent) ((GraduateStudent) researcher).addPaper(paper);
                        researcher.publishPaper();
                        System.out.println("Paper added: " + title + " | Author: " + authorName + " | Citations: 0");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "2": {
                    List<ResearchPaper> papers = null;
                    if (researcher instanceof Teacher) papers = ((Teacher) researcher).getPapers();
                    else if (researcher instanceof GraduateStudent) papers = ((GraduateStudent) researcher).getPapers();
                    if (papers == null || papers.isEmpty()) { System.out.println("No papers."); break; }
                    for (ResearchPaper p : papers)
                        System.out.println("  " + p.getTitle() + " | Journal: " + p.getJournal() + " | Citations: " + p.getCitations());
                    break;
                }
                case "3":
                    System.out.println("H-Index: " + researcher.calculateHIndex());
                    break;
                case "4": {
                    System.out.println("Sort by: 1. Citations  2. Date  3. Pages");
                    System.out.print("Choice: ");
                    String sort = scanner.nextLine().trim();
                    switch (sort) {
                        case "1": researcher.printPapers(new CitationComparator()); break;
                        case "2": researcher.printPapers(new DateComparator()); break;
                        case "3": researcher.printPapers(new PageCountComparator()); break;
                        default:  System.out.println("Invalid sort option."); break;
                    }
                    break;
                }
                case "5":
                    researcher.joinResearchProject();
                    break;
                case "6": {
                    List<ResearchPaper> papers = null;
                    if (researcher instanceof Teacher) papers = ((Teacher) researcher).getPapers();
                    else if (researcher instanceof GraduateStudent) papers = ((GraduateStudent) researcher).getPapers();
                    if (papers == null || papers.isEmpty()) { System.out.println("No papers available."); break; }
                    for (int i = 0; i < papers.size(); i++)
                        System.out.println((i + 1) + ". " + papers.get(i).getTitle() + " | Citations: " + papers.get(i).getCitations());
                    System.out.print("Select paper: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= papers.size()) { System.out.println("Invalid."); break; }
                        System.out.print("Citations to add: ");
                        int count = Integer.parseInt(scanner.nextLine().trim());
                        papers.get(idx).addCitation(count);
                        System.out.println("Updated citations: " + papers.get(idx).getCitations());
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "7":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ===================== MANAGER MENU =====================
    static void managerMenu(Manager mgr) {
        while (true) {
            System.out.println("\n=== MANAGER MENU ===");
            System.out.println("1. Assign course to teacher");
            System.out.println("2. Approve registration");
            System.out.println("3. Create report");
            System.out.println("4. Manage news");
            System.out.println("5. View news");
            System.out.println("6. View complaints");
            System.out.println("7. Process complaint");
            System.out.println("8. Send request to tech");
            System.out.println("9. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Teacher) teachers.add((Teacher) u);
                    if (teachers.isEmpty()) { System.out.println("No teachers."); break; }
                    for (int i = 0; i < teachers.size(); i++)
                        System.out.println((i + 1) + ". " + teachers.get(i).getName());
                    System.out.print("Select teacher: ");
                    try {
                        int tIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (tIdx < 0 || tIdx >= teachers.size()) { System.out.println("Invalid."); break; }
                        for (int i = 0; i < allCourses.size(); i++)
                            System.out.println((i + 1) + ". " + allCourses.get(i).getTitle());
                        System.out.print("Select course: ");
                        int cIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (cIdx < 0 || cIdx >= allCourses.size()) { System.out.println("Invalid."); break; }
                        mgr.assignCourse(allCourses.get(cIdx), teachers.get(tIdx));
                        System.out.println("Course assigned to " + teachers.get(tIdx).getName() + ".");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "2": {
                    List<Student> students = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Student) students.add((Student) u);
                    if (students.isEmpty()) { System.out.println("No students."); break; }
                    for (int i = 0; i < students.size(); i++)
                        System.out.println((i + 1) + ". " + students.get(i).getName());
                    System.out.print("Select student: ");
                    try {
                        int sIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (sIdx < 0 || sIdx >= students.size()) { System.out.println("Invalid."); break; }
                        for (int i = 0; i < allCourses.size(); i++)
                            System.out.println((i + 1) + ". " + allCourses.get(i).getTitle());
                        System.out.print("Select course: ");
                        int cIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (cIdx < 0 || cIdx >= allCourses.size()) { System.out.println("Invalid."); break; }
                        Registration reg = new Registration(students.get(sIdx), allCourses.get(cIdx));
                        try {
                            mgr.approveRegistration(reg);
                            System.out.println("Status: " + reg.getStatus());
                        } catch (CreditLimitExceededException | RetakeLimitExceededException e) {
                            System.out.println("[ERROR] " + e.getMessage());
                        }
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "3":
                    mgr.createReport();
                    break;
                case "4": {
                    System.out.print("Title: ");   String title   = scanner.nextLine().trim();
                    System.out.print("Content: "); String content = scanner.nextLine().trim();
                    News news = new News(title, content);
                    mgr.manageNews(news);
                    allNews.add(news);
                    break;
                }
                case "5":
                    if (allNews.isEmpty()) System.out.println("No news available.");
                    else allNews.forEach(n -> System.out.println("[" + n.getDate() + "] " + n.getTitle() + ": " + n.getContent()));
                    break;
                case "6": {
                    if (allComplaints.isEmpty()) { System.out.println("No complaints."); break; }
                    for (int i = 0; i < allComplaints.size(); i++) {
                        Complaint c = allComplaints.get(i);
                        System.out.println((i + 1) + ". From: " + c.getTeacher().getName() +
                                " | About: " + c.getStudent().getName() +
                                " | Urgency: " + c.getUrgency() +
                                " | Status: " + c.getStatus());
                        System.out.println("   Text: " + c.getText());
                    }
                    break;
                }
                case "7": {
                    if (allComplaints.isEmpty()) { System.out.println("No complaints."); break; }
                    for (int i = 0; i < allComplaints.size(); i++) {
                        Complaint c = allComplaints.get(i);
                        System.out.println((i + 1) + ". " + c.getTeacher().getName() + " → " +
                                c.getStudent().getName() + " | " + c.getUrgency() + " | " + c.getStatus());
                    }
                    System.out.print("Select complaint to process: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= allComplaints.size()) { System.out.println("Invalid."); break; }
                        mgr.processComplaint(allComplaints.get(idx));
                        System.out.println("Status updated: " + allComplaints.get(idx).getStatus());
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "8": {
                    List<TechSupportSpecialist> techs = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof TechSupportSpecialist) techs.add((TechSupportSpecialist) u);
                    if (techs.isEmpty()) { System.out.println("No tech support specialists."); break; }
                    for (int i = 0; i < techs.size(); i++)
                        System.out.println((i + 1) + ". " + techs.get(i).getName());
                    System.out.print("Select specialist: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= techs.size()) { System.out.println("Invalid."); break; }
                        System.out.print("Request description: ");
                        String desc = scanner.nextLine().trim();
                        Request req = new Request(desc);
                        techs.get(idx).addRequest(req);
                        System.out.println("Request sent to " + techs.get(idx).getName() + ". Status: " + req.getStatus());
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "9":
                    mgr.logout();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ===================== TECH SUPPORT MENU =====================
    static void techMenu(TechSupportSpecialist tech) {
        while (true) {
            System.out.println("\n=== TECH SUPPORT MENU ===");
            System.out.println("1. View requests");
            System.out.println("2. Accept request");
            System.out.println("3. Reject request");
            System.out.println("4. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    List<Request> reqs = tech.viewRequests();
                    if (reqs.isEmpty()) System.out.println("No requests.");
                    else for (int i = 0; i < reqs.size(); i++)
                        System.out.println((i + 1) + ". " + reqs.get(i));
                    break;
                }
                case "2": {
                    List<Request> reqs = tech.viewRequests();
                    if (reqs.isEmpty()) { System.out.println("No requests."); break; }
                    for (int i = 0; i < reqs.size(); i++) System.out.println((i + 1) + ". " + reqs.get(i));
                    System.out.print("Select request: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < reqs.size()) {
                            reqs.get(idx).view();
                            tech.acceptRequest(reqs.get(idx));
                            System.out.println("Accepted. Status: " + reqs.get(idx).getStatus());
                        } else System.out.println("Invalid.");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "3": {
                    List<Request> reqs = tech.viewRequests();
                    if (reqs.isEmpty()) { System.out.println("No requests."); break; }
                    for (int i = 0; i < reqs.size(); i++) System.out.println((i + 1) + ". " + reqs.get(i));
                    System.out.print("Select request: ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < reqs.size()) {
                            reqs.get(idx).view();
                            tech.rejectRequest(reqs.get(idx));
                            System.out.println("Rejected. Status: " + reqs.get(idx).getStatus());
                        } else System.out.println("Invalid.");
                    } catch (NumberFormatException e) { System.out.println("Invalid input."); }
                    break;
                }
                case "4":
                    tech.logout();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
