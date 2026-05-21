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

    static final UniversityData   data          = UniversityData.getInstance();
    static final List<User>       allUsers      = data.getUsers();
    static final List<Course>     allCourses    = data.getCourses();
    static final List<News>       allNews       = data.getNews();
    static final List<Complaint>  allComplaints = data.getComplaints();
    static final Map<String, String> credentials = data.getCredentials();
    static final Scanner scanner = new Scanner(System.in);
    static Language selectedLanguage = Language.EN;

    static final Map<String, String[]> TR = new HashMap<>();
    static {
        TR.put("login.title",       new String[]{"UNIVERSITY SYSTEM LOGIN",            "ВХОД В СИСТЕМУ УНИВЕРСИТЕТА",      "УНИВЕРСИТЕТ ЖҮЙЕСІНЕ КІРУ"});
        TR.put("login.username",    new String[]{"Username: ",                          "Имя пользователя: ",               "Пайдаланушы аты: "});
        TR.put("login.password",    new String[]{"Password: ",                          "Пароль: ",                         "Құпия сөз: "});
        TR.put("login.welcome",     new String[]{"Welcome, ",                           "Добро пожаловать, ",               "Қош келдіңіз, "});
        TR.put("err.notfound",      new String[]{"[ERROR] User not found.",             "[ОШИБКА] Пользователь не найден.", "[ҚАТЕ] Пайдаланушы табылмады."});
        TR.put("err.password",      new String[]{"[ERROR] Incorrect password.",         "[ОШИБКА] Неверный пароль.",        "[ҚАТЕ] Қате құпия сөз."});
        TR.put("choice",            new String[]{"Choice: ",                            "Выбор: ",                          "Таңдау: "});
        TR.put("invalid.choice",    new String[]{"Invalid choice.",                     "Неверный выбор.",                  "Жарамсыз таңдау."});
        TR.put("invalid.input",     new String[]{"Invalid input.",                      "Неверный ввод.",                   "Жарамсыз енгізу."});
        TR.put("invalid.sel",       new String[]{"Invalid selection.",                  "Неверный выбор.",                  "Жарамсыз таңдау."});
        TR.put("no.users",          new String[]{"No users.",                           "Пользователи отсутствуют.",        "Пайдаланушылар жоқ."});
        TR.put("no.news",           new String[]{"No news available.",                  "Нет новостей.",                    "Жаңалықтар жоқ."});
        TR.put("no.complaints",     new String[]{"No complaints.",                      "Жалоб нет.",                      "Шағымдар жоқ."});
        TR.put("no.papers",         new String[]{"No papers.",                          "Статей нет.",                      "Мақалалар жоқ."});
        TR.put("no.requests",       new String[]{"No requests.",                        "Запросов нет.",                    "Сұраныстар жоқ."});
        TR.put("no.courses",        new String[]{"No courses assigned.",                "Курсы не назначены.",              "Курстар тағайындалмаған."});
        TR.put("no.students",       new String[]{"No students.",                        "Студентов нет.",                   "Студенттер жоқ."});
        TR.put("no.teachers",       new String[]{"No teachers available.",              "Преподавателей нет.",              "Оқытушылар жоқ."});
        TR.put("no.enrolled",       new String[]{"No enrolled students.",               "Нет записавшихся студентов.",      "Тіркелген студенттер жоқ."});
        TR.put("no.marks",          new String[]{"No marks yet.",                       "Оценок нет.",                      "Бағалар жоқ."});
        TR.put("no.logs",           new String[]{"No logs.",                            "Журнал пуст.",                     "Журнал бос."});
        TR.put("no.tech",           new String[]{"No tech support specialists.",        "Специалистов техподдержки нет.",   "Техқолдау мамандары жоқ."});
        TR.put("no.papers.avail",   new String[]{"No papers available.",                "Статей нет.",                      "Мақалалар жоқ."});

        TR.put("admin.title",       new String[]{"=== ADMIN MENU ===",                 "=== МЕНЮ АДМИНИСТРАТОРА ===",      "=== ӘКІМШІ МӘЗІРІ ==="});
        TR.put("admin.1",           new String[]{"1. Add user",                         "1. Добавить пользователя",         "1. Пайдаланушы қосу"});
        TR.put("admin.2",           new String[]{"2. Remove user",                      "2. Удалить пользователя",          "2. Пайдаланушыны жою"});
        TR.put("admin.3",           new String[]{"3. View logs",                        "3. Просмотр журнала",              "3. Журналды көру"});
        TR.put("admin.4",           new String[]{"4. View news",                        "4. Просмотр новостей",             "4. Жаңалықтарды көру"});
        TR.put("admin.5",           new String[]{"5. Logout",                           "5. Выход",                         "5. Шығу"});

        TR.put("student.title",     new String[]{"=== STUDENT MENU ===",               "=== МЕНЮ СТУДЕНТА ===",            "=== СТУДЕНТ МӘЗІРІ ==="});
        TR.put("student.1",         new String[]{"1. Register for course",              "1. Записаться на курс",            "1. Курсқа тіркелу"});
        TR.put("student.2",         new String[]{"2. View marks",                       "2. Просмотр оценок",               "2. Бағаларды көру"});
        TR.put("student.3",         new String[]{"3. View transcript",                  "3. Просмотр транскрипта",          "3. Транскриптті көру"});
        TR.put("student.4",         new String[]{"4. View news",                        "4. Просмотр новостей",             "4. Жаңалықтарды көру"});
        TR.put("student.5",         new String[]{"5. Rate teacher",                     "5. Оценить преподавателя",         "5. Оқытушыны бағалау"});
        TR.put("student.6",         new String[]{"6. View teacher info",                "6. Информация о преподавателе",    "6. Оқытушы туралы ақпарат"});
        TR.put("student.7r",        new String[]{"7. Research Menu",                    "7. Меню исследований",             "7. Зерттеу мәзірі"});
        TR.put("student.7",         new String[]{"7. Logout",                           "7. Выход",                         "7. Шығу"});
        TR.put("student.8",         new String[]{"8. Logout",                           "8. Выход",                         "8. Шығу"});

        TR.put("teacher.title",     new String[]{"=== TEACHER MENU ===",               "=== МЕНЮ ПРЕПОДАВАТЕЛЯ ===",       "=== ОҚЫТУШЫ МӘЗІРІ ==="});
        TR.put("teacher.1",         new String[]{"1. Manage course",                    "1. Управление курсом",             "1. Курсты басқару"});
        TR.put("teacher.2",         new String[]{"2. Put mark",                         "2. Выставить оценку",              "2. Баға қою"});
        TR.put("teacher.3",         new String[]{"3. Send message",                     "3. Отправить сообщение",           "3. Хабарлама жіберу"});
        TR.put("teacher.4",         new String[]{"4. Send complaint",                   "4. Отправить жалобу",              "4. Шағым жіберу"});
        TR.put("teacher.5",         new String[]{"5. Research Menu",                    "5. Меню исследований",             "5. Зерттеу мәзірі"});
        TR.put("teacher.6",         new String[]{"6. Logout",                           "6. Выход",                         "6. Шығу"});

        TR.put("manager.title",     new String[]{"=== MANAGER MENU ===",               "=== МЕНЮ МЕНЕДЖЕРА ===",           "=== МЕНЕДЖЕР МӘЗІРІ ==="});
        TR.put("manager.1",         new String[]{"1. Assign course to teacher",         "1. Назначить курс преподавателю",  "1. Оқытушыға курс тағайындау"});
        TR.put("manager.2",         new String[]{"2. Approve registration",             "2. Подтвердить регистрацию",       "2. Тіркелуді растау"});
        TR.put("manager.3",         new String[]{"3. Create report",                    "3. Создать отчёт",                 "3. Есеп жасау"});
        TR.put("manager.4",         new String[]{"4. Manage news",                      "4. Управление новостями",          "4. Жаңалықтарды басқару"});
        TR.put("manager.5",         new String[]{"5. View news",                        "5. Просмотр новостей",             "5. Жаңалықтарды көру"});
        TR.put("manager.6",         new String[]{"6. View complaints",                  "6. Просмотр жалоб",                "6. Шағымдарды көру"});
        TR.put("manager.7",         new String[]{"7. Process complaint",                "7. Обработать жалобу",             "7. Шағымды өңдеу"});
        TR.put("manager.8",         new String[]{"8. Send request to tech",             "8. Запрос в техподдержку",         "8. Техқолдауға сұраныс"});
        TR.put("manager.9",         new String[]{"9. Logout",                           "9. Выход",                         "9. Шығу"});

        TR.put("research.title",    new String[]{"=== RESEARCH MENU ===",              "=== МЕНЮ ИССЛЕДОВАНИЙ ===",        "=== ЗЕРТТЕУ МӘЗІРІ ==="});
        TR.put("research.1",        new String[]{"1. Publish Paper",                    "1. Опубликовать статью",           "1. Мақала жариялау"});
        TR.put("research.2",        new String[]{"2. View My Papers",                   "2. Мои статьи",                    "2. Менің мақалаларым"});
        TR.put("research.3",        new String[]{"3. Calculate H-Index",                "3. Вычислить H-индекс",            "3. H-индексті есептеу"});
        TR.put("research.4",        new String[]{"4. Print Papers (sorted)",            "4. Вывести статьи (сортировка)",   "4. Мақалаларды шығару"});
        TR.put("research.5",        new String[]{"5. Join Research Project",            "5. Присоединиться к проекту",      "5. Жобаға қосылу"});
        TR.put("research.6",        new String[]{"6. Simulate Citation",                "6. Симулировать цитирование",      "6. Сілтемені модельдеу"});
        TR.put("research.7",        new String[]{"7. Back to Main Menu",                "7. Вернуться в меню",              "7. Мәзірге оралу"});

        TR.put("tech.title",        new String[]{"=== TECH SUPPORT MENU ===",          "=== МЕНЮ ТЕХПОДДЕРЖКИ ===",        "=== ТЕХҚОЛДАУ МӘЗІРІ ==="});
        TR.put("tech.1",            new String[]{"1. View requests",                    "1. Просмотр запросов",             "1. Сұраныстарды көру"});
        TR.put("tech.2",            new String[]{"2. Accept request",                   "2. Принять запрос",                "2. Сұранысты қабылдау"});
        TR.put("tech.3",            new String[]{"3. Reject request",                   "3. Отклонить запрос",              "3. Сұранысты қабылдамау"});
        TR.put("tech.4",            new String[]{"4. Logout",                           "4. Выход",                         "4. Шығу"});

        TR.put("p.username",        new String[]{"Username: ",                          "Имя пользователя: ",               "Пайдаланушы аты: "});
        TR.put("p.password",        new String[]{"Password: ",                          "Пароль: ",                         "Құпия сөз: "});
        TR.put("p.fullname",        new String[]{"Full name: ",                         "Полное имя: ",                     "Толық аты: "});
        TR.put("p.sel.course",      new String[]{"Select course: ",                     "Выберите курс: ",                  "Курсты таңдаңыз: "});
        TR.put("p.sel.teacher",     new String[]{"Select teacher: ",                    "Выберите преподавателя: ",         "Оқытушыны таңдаңыз: "});
        TR.put("p.sel.student",     new String[]{"Select student: ",                    "Выберите студента: ",              "Студентті таңдаңыз: "});
        TR.put("p.sel.user",        new String[]{"Select user to remove: ",             "Выберите пользователя: ",          "Пайдаланушыны таңдаңыз: "});
        TR.put("p.sel.recipient",   new String[]{"Select recipient: ",                  "Выберите получателя: ",            "Алушыны таңдаңыз: "});
        TR.put("p.sel.specialist",  new String[]{"Select specialist: ",                 "Выберите специалиста: ",           "Маманды таңдаңыз: "});
        TR.put("p.sel.complaint",   new String[]{"Select complaint to process: ",       "Выберите жалобу: ",                "Шағымды таңдаңыз: "});
        TR.put("p.sel.paper",       new String[]{"Select paper: ",                      "Выберите статью: ",                "Мақаланы таңдаңыз: "});
        TR.put("p.message",         new String[]{"Message: ",                           "Сообщение: ",                      "Хабарлама: "});
        TR.put("p.rating",          new String[]{"Rating (1-5): ",                      "Оценка (1-5): ",                   "Рейтинг (1-5): "});
        TR.put("p.complaint.text",  new String[]{"Complaint text: ",                    "Текст жалобы: ",                   "Шағым мәтіні: "});
        TR.put("p.urgency",         new String[]{"Urgency: 1.LOW  2.MEDIUM  3.HIGH",   "Срочность: 1.НИЗКАЯ  2.СРЕДНЯЯ  3.ВЫСОКАЯ", "Шұғылдық: 1.ТӨМЕН  2.ОРТА  3.ЖОҒАРЫ"});
        TR.put("p.first.att",       new String[]{"First attestation: ",                 "Первая аттестация: ",              "Бірінші аттестация: "});
        TR.put("p.second.att",      new String[]{"Second attestation: ",                "Вторая аттестация: ",              "Екінші аттестация: "});
        TR.put("p.final",           new String[]{"Final exam: ",                        "Финальный экзамен: ",              "Финалдық емтихан: "});
        TR.put("p.title",           new String[]{"Title: ",                             "Заголовок: ",                      "Тақырып: "});
        TR.put("p.content",         new String[]{"Content: ",                           "Содержание: ",                     "Мазмұн: "});
        TR.put("p.journal",         new String[]{"Journal: ",                           "Журнал: ",                         "Журнал: "});
        TR.put("p.pages",           new String[]{"Pages: ",                             "Страниц: ",                        "Беттер: "});
        TR.put("p.citations",       new String[]{"Citations to add: ",                  "Цитирований добавить: ",           "Сілтемелер қосу: "});
        TR.put("p.request",         new String[]{"Request description: ",               "Описание запроса: ",               "Сұраныс сипаттамасы: "});

        TR.put("m.user.added",      new String[]{"User added: ",                        "Пользователь добавлен: ",          "Пайдаланушы қосылды: "});
        TR.put("m.user.removed",    new String[]{"Removed: ",                           "Удалён: ",                         "Жойылды: "});
        TR.put("m.enrolled",        new String[]{"Enrolled in: ",                       "Записан на курс: ",                "Курсқа тіркелді: "});
        TR.put("m.cannot.reg",      new String[]{"Cannot register: course full or already enrolled.", "Запись невозможна: курс заполнен или уже записан.", "Тіркелу мүмкін емес: курс толы немесе тіркелген."});
        TR.put("m.mark.assigned",   new String[]{"Mark assigned: ",                     "Оценка выставлена: ",              "Баға қойылды: "});
        TR.put("m.att.fail",        new String[]{"Attestation < 30. Grade: F",          "Аттестация < 30. Оценка: F",       "Аттестация < 30. Баға: F"});
        TR.put("m.msg.sent",        new String[]{"Message sent to ",                    "Сообщение отправлено ",            "Хабарлама жіберілді "});
        TR.put("m.complaint.sent",  new String[]{"Complaint sent. Status: ",            "Жалоба отправлена. Статус: ",      "Шағым жіберілді. Мәртебе: "});
        TR.put("m.course.assigned", new String[]{"Course assigned to ",                 "Курс назначен ",                   "Курс тағайындалды "});
        TR.put("m.reg.status",      new String[]{"Status: ",                            "Статус: ",                         "Мәртебе: "});
        TR.put("m.req.sent",        new String[]{"Request sent to ",                    "Запрос отправлен ",                "Сұраныс жіберілді "});
        TR.put("m.complaint.upd",   new String[]{"Status updated: ",                    "Статус обновлён: ",                "Мәртебе жаңартылды: "});
        TR.put("m.accepted",        new String[]{"Accepted. Status: ",                  "Принято. Статус: ",                "Қабылданды. Мәртебе: "});
        TR.put("m.rejected",        new String[]{"Rejected. Status: ",                  "Отклонено. Статус: ",              "Қабылданбады. Мәртебе: "});
        TR.put("m.paper.added",     new String[]{"Paper added: ",                       "Статья добавлена: ",               "Мақала қосылды: "});
        TR.put("m.citations.upd",   new String[]{"Updated citations: ",                 "Цитирований обновлено: ",          "Сілтемелер жаңартылды: "});
        TR.put("m.hindex",          new String[]{"H-Index: ",                           "H-индекс: ",                       "H-индекс: "});
        TR.put("m.sort",            new String[]{"Sort by: 1.Citations  2.Date  3.Pages","Сортировка: 1.Цитир  2.Дата  3.Стр","Сұрыптау: 1.Сілтеме  2.Күні  3.Бет"});
        TR.put("m.sort.invalid",    new String[]{"Invalid sort option.",                 "Неверный вариант сортировки.",     "Жарамсыз сұрыптау."});
        TR.put("m.rating.invalid",  new String[]{"Rating must be 1-5.",                 "Оценка должна быть 1-5.",           "Рейтинг 1-5 болуы керек."});
        TR.put("m.max.att",         new String[]{"Maximum f+s attestation: 60",         "Максимум 1+2 аттестация: 60",      "Максималды 1+2 аттестация: 60"});
        TR.put("m.max.final",       new String[]{"Maximum: 40",                         "Максимум: 40",                     "Максималды: 40"});
        TR.put("m.max.total",       new String[]{"Maximum: 100",                        "Максимум: 100",                    "Максималды: 100"});
        TR.put("m.avail.courses",   new String[]{"Available courses:",                  "Доступные курсы:",                 "Қолжетімді курстар:"});
        TR.put("m.teacher.info",    new String[]{"--- Teacher Info ---",                "--- Информация о преподавателе ---","--- Оқытушы туралы ақпарат ---"});
        TR.put("m.name",            new String[]{"Name:     ",                          "Имя:      ",                       "Аты:      "});
        TR.put("m.type",            new String[]{"Type:     ",                          "Тип:      ",                       "Түрі:     "});
        TR.put("m.courses.count",   new String[]{"Courses:  ",                          "Курсы:    ",                       "Курстар:  "});
        TR.put("m.papers.count",    new String[]{"Papers:   ",                          "Статьи:   ",                       "Мақалалар:"});
        TR.put("m.your.courses",    new String[]{"Your courses:",                       "Ваши курсы:",                      "Сіздің курстарыңыз:"});
        TR.put("m.urgency",         new String[]{"| Urgency: ",                         "| Срочность: ",                    "| Шұғылдық: "});
        TR.put("m.status",          new String[]{"| Status: ",                          "| Статус: ",                       "| Мәртебе: "});
        TR.put("m.text",            new String[]{"   Text: ",                           "   Текст: ",                       "   Мәтін: "});
        TR.put("m.from",            new String[]{"From: ",                              "От: ",                             "Кімнен: "});
        TR.put("m.about",           new String[]{"| About: ",                           "| О студенте: ",                   "| Студент: "});
    }

    static String t(String key) {
        String[] vals = TR.get(key);
        if (vals == null) return key;
        int i = selectedLanguage == Language.RU ? 1 : selectedLanguage == Language.KZ ? 2 : 0;
        return vals[i];
    }

    public static void main(String[] args) {
        setupSystem();
        selectLanguage();
        while (true) {
            User user = loginScreen();
            if (user != null) showMenu(user);
        }
    }

    static void selectLanguage() {
        System.out.println("\n========================================");
        System.out.println("  Select language / Выберите язык / Тілді таңдаңыз");
        System.out.println("========================================");
        System.out.println("1. English");
        System.out.println("2. Русский");
        System.out.println("3. Қазақша");
        System.out.print("Choice / Выбор / Таңдау: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "2": selectedLanguage = Language.RU; break;
            case "3": selectedLanguage = Language.KZ; break;
            default:  selectedLanguage = Language.EN; break;
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

        ResearchPaper p1 = new ResearchPaper("AI in Education", "IEEE", new Date(), "Teacher", 10); p1.setCitations(10);
        ResearchPaper p2 = new ResearchPaper("ML Algorithms", "ACM", new Date(), "Teacher", 8);     p2.setCitations(8);
        ResearchPaper p3 = new ResearchPaper("Deep Learning", "Nature", new Date(), "Teacher", 5);  p3.setCitations(5);
        teacher.addPaper(p1); teacher.addPaper(p2); teacher.addPaper(p3);

        GraduateStudent graduate = new GraduateStudent(6, "graduate", "graduate", "Graduate", Language.EN, "STU002", 1, 0.0, 0, DegreeType.MASTER, "AI Research", teacher);
        credentials.put("graduate", "graduate");
        allUsers.add(graduate);
        admin.addUser(graduate);

        Course math    = new Course("MATH101", "Calculus I", 6, 30);
        Course cs      = new Course("CS101",   "Intro to CS", 5, 25);
        Course physics = new Course("PHYS101", "Physics I", 4, 20);
        teacher.manageCourse(math); teacher.manageCourse(cs);
        allCourses.addAll(Arrays.asList(math, cs, physics));

        TechSupportSpecialist techRef = (TechSupportSpecialist) allUsers.get(4);
        techRef.addRequest(new Request("Projector broken in Room 101"));
        techRef.addRequest(new Request("Network issue in Lab 3"));

        allNews.add(new News("Welcome to Spring Semester", "Classes begin February 1."));
    }

    static User loginScreen() {
        System.out.println("\n========================================");
        System.out.println("  " + t("login.title"));
        System.out.println("========================================");
        System.out.print(t("login.username")); String username = scanner.nextLine().trim();
        System.out.print(t("login.password")); String password = scanner.nextLine().trim();

        User found = null;
        for (User u : allUsers) { if (u.getUsername().equals(username)) { found = u; break; } }
        if (found == null) { System.out.println(t("err.notfound")); return null; }
        if (!credentials.getOrDefault(username, "").equals(password)) { System.out.println(t("err.password")); return null; }
        try {
            found.login();
            System.out.println(t("login.welcome") + found.getName() + "!");
            return found;
        } catch (Exception e) { System.out.println("[ERROR] " + e.getMessage()); return null; }
    }

    static void showMenu(User user) {
        if      (user instanceof Admin)                 adminMenu((Admin) user);
        else if (user instanceof Manager)               managerMenu((Manager) user);
        else if (user instanceof Teacher)               teacherMenu((Teacher) user);
        else if (user instanceof GraduateStudent)       studentMenu((GraduateStudent) user);
        else if (user instanceof Student)               studentMenu((Student) user);
        else if (user instanceof TechSupportSpecialist) techMenu((TechSupportSpecialist) user);
    }

    static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n" + t("admin.title"));
            System.out.println(t("admin.1")); System.out.println(t("admin.2"));
            System.out.println(t("admin.3")); System.out.println(t("admin.4"));
            System.out.println(t("admin.5"));
            System.out.print(t("choice")); String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    System.out.print(t("p.username")); String uname = scanner.nextLine().trim();
                    System.out.print(t("p.password")); String pwd   = scanner.nextLine().trim();
                    System.out.print(t("p.fullname")); String name  = scanner.nextLine().trim();
                    Student ns = new Student(allUsers.size() + 1, uname, pwd, name, Language.EN, "STU" + allUsers.size(), 1, 0.0, 0);
                    admin.addUser(ns); allUsers.add(ns); credentials.put(uname, pwd);
                    System.out.println(t("m.user.added") + name); break;
                }
                case "2": {
                    if (allUsers.isEmpty()) { System.out.println(t("no.users")); break; }
                    for (int i = 0; i < allUsers.size(); i++)
                        System.out.println((i+1) + ". " + allUsers.get(i).getName() + " [" + allUsers.get(i).getUsername() + "]");
                    System.out.print(t("p.sel.user"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < allUsers.size()) {
                            User u = allUsers.get(idx); admin.removeUser(u); allUsers.remove(u);
                            System.out.println(t("m.user.removed") + u.getName());
                        } else System.out.println(t("invalid.sel"));
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "3": {
                    List<LogRecord> logs = admin.viewLogs();
                    if (logs.isEmpty()) System.out.println(t("no.logs")); else logs.forEach(System.out::println); break;
                }
                case "4":
                    if (allNews.isEmpty()) System.out.println(t("no.news"));
                    else allNews.forEach(n -> System.out.println("[" + n.getDate() + "] " + n.getTitle() + ": " + n.getContent())); break;
                case "5": admin.logout(); return;
                default: System.out.println(t("invalid.choice"));
            }
        }
    }

    static void studentMenu(Student student) {
        boolean isResearcher = student instanceof Researcher;
        while (true) {
            System.out.println("\n" + t("student.title"));
            System.out.println(t("student.1")); System.out.println(t("student.2"));
            System.out.println(t("student.3")); System.out.println(t("student.4"));
            System.out.println(t("student.5")); System.out.println(t("student.6"));
            if (isResearcher) System.out.println(t("student.7r"));
            System.out.println(isResearcher ? t("student.8") : t("student.7"));
            System.out.print(t("choice")); String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    System.out.println(t("m.avail.courses"));
                    for (int i = 0; i < allCourses.size(); i++) {
                        Course c = allCourses.get(i);
                        System.out.println((i+1) + ". " + c.getTitle() + " (" + c.getCourseCode() + ") | " + c.getCredits() + " cr | " + (c.getCapacity()-c.getEnrolledCount()) + " left");
                    }
                    System.out.print(t("p.sel.course"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= allCourses.size()) { System.out.println(t("invalid.sel")); break; }
                        Registration reg = new Registration(student, allCourses.get(idx));
                        try {
                            if (reg.isValid()) { student.registerForCourse(allCourses.get(idx)); System.out.println(t("m.enrolled") + allCourses.get(idx).getTitle()); }
                            else System.out.println(t("m.cannot.reg"));
                        } catch (CreditLimitExceededException | RetakeLimitExceededException e) { System.out.println("[ERROR] " + e.getMessage()); }
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "2": {
                    List<Mark> marks = student.viewMark();
                    if (marks.isEmpty()) System.out.println(t("no.marks"));
                    else marks.forEach(m -> System.out.println("Total: " + m.getTotalMark() + " | " + m.getLetterGrade() + " | Passed: " + m.isPassed())); break;
                }
                case "3": student.viewTranscript().generateTranscript(); break;
                case "4":
                    if (allNews.isEmpty()) System.out.println(t("no.news"));
                    else allNews.forEach(n -> System.out.println("[" + n.getDate() + "] " + n.getTitle() + ": " + n.getContent())); break;
                case "5": {
                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Teacher) teachers.add((Teacher) u);
                    if (teachers.isEmpty()) { System.out.println(t("no.teachers")); break; }
                    for (int i = 0; i < teachers.size(); i++) System.out.println((i+1) + ". " + teachers.get(i).getName());
                    System.out.print(t("p.sel.teacher"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= teachers.size()) { System.out.println(t("invalid.sel")); break; }
                        System.out.print(t("p.rating"));
                        int rate = Integer.parseInt(scanner.nextLine().trim());
                        if (rate < 1 || rate > 5) { System.out.println(t("m.rating.invalid")); break; }
                        student.rateTeacher(teachers.get(idx), rate);
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "6": {
                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Teacher) teachers.add((Teacher) u);
                    if (teachers.isEmpty()) { System.out.println(t("no.teachers")); break; }
                    for (int i = 0; i < teachers.size(); i++) System.out.println((i+1) + ". " + teachers.get(i).getName());
                    System.out.print(t("p.sel.teacher"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= teachers.size()) { System.out.println(t("invalid.sel")); break; }
                        Teacher te = teachers.get(idx);
                        System.out.println(t("m.teacher.info"));
                        System.out.println(t("m.name")     + te.getName());
                        System.out.println(t("m.type")     + te.getTeacherType());
                        System.out.println(t("m.courses.count") + te.getCourses().size());
                        System.out.println(t("m.papers.count")  + te.getPapers().size());
                        System.out.println(t("m.hindex")    + te.calculateHIndex());
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "7":
                    if (isResearcher) { researchMenu((Researcher) student); break; }
                    student.logout(); return;
                case "8":
                    if (isResearcher) { student.logout(); return; }
                    System.out.println(t("invalid.choice")); break;
                default: System.out.println(t("invalid.choice"));
            }
        }
    }

    static void teacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("\n" + t("teacher.title"));
            System.out.println(t("teacher.1")); System.out.println(t("teacher.2"));
            System.out.println(t("teacher.3")); System.out.println(t("teacher.4"));
            System.out.println(t("teacher.5")); System.out.println(t("teacher.6"));
            System.out.print(t("choice")); String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    List<Course> courses = teacher.getCourses();
                    if (courses.isEmpty()) { System.out.println(t("no.courses")); break; }
                    System.out.println(t("m.your.courses"));
                    courses.forEach(c -> {
                        System.out.println("  [" + c.getCourseCode() + "] " + c.getTitle() + " | " + c.getEnrolledCount() + "/" + c.getCapacity());
                        c.getLessons().forEach(l -> System.out.println("    " + l.getLessonInfo()));
                    }); break;
                }
                case "2": {
                    List<Course> courses = teacher.getCourses();
                    if (courses.isEmpty()) { System.out.println(t("no.courses")); break; }
                    List<Student> enrolled = new ArrayList<>();
                    for (Course c : courses) for (Student s : c.getEnrolledStudents()) if (!enrolled.contains(s)) enrolled.add(s);
                    if (enrolled.isEmpty()) { System.out.println(t("no.enrolled")); break; }
                    for (int i = 0; i < enrolled.size(); i++) System.out.println((i+1) + ". " + enrolled.get(i).getName());
                    System.out.print(t("p.sel.student"));
                    try {
                        int sIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (sIdx < 0 || sIdx >= enrolled.size()) { System.out.println(t("invalid.sel")); break; }
                        for (int i = 0; i < courses.size(); i++) System.out.println((i+1) + ". " + courses.get(i).getTitle());
                        System.out.print(t("p.sel.course"));
                        int cIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (cIdx < 0 || cIdx >= courses.size()) { System.out.println(t("invalid.sel")); break; }
                        System.out.print(t("p.first.att"));  double fa = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print(t("p.second.att")); double sa = Double.parseDouble(scanner.nextLine().trim());
                        if (fa + sa > 60) { System.out.println(t("m.max.att")); break; }
                        if (fa + sa < 30) {
                            Mark mark = new Mark(fa, sa, 0);
                            teacher.putMark(enrolled.get(sIdx), courses.get(cIdx), mark);
                            System.out.println(t("m.att.fail")); break;
                        }
                        System.out.print(t("p.final")); double fe = Double.parseDouble(scanner.nextLine().trim());
                        if (fe > 40) { System.out.println(t("m.max.final")); break; }
                        if (fa + sa + fe > 100) { System.out.println(t("m.max.total")); break; }
                        Mark mark = new Mark(fa, sa, fe);
                        teacher.putMark(enrolled.get(sIdx), courses.get(cIdx), mark);
                        System.out.println(t("m.mark.assigned") + mark.getTotalMark() + " (" + mark.getLetterGrade() + ")");
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "3": {
                    for (int i = 0; i < allUsers.size(); i++) System.out.println((i+1) + ". " + allUsers.get(i).getName());
                    System.out.print(t("p.sel.recipient"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= allUsers.size()) { System.out.println(t("invalid.sel")); break; }
                        User recipient = allUsers.get(idx);
                        System.out.print(t("p.message")); String content = scanner.nextLine().trim();
                        teacher.sendMessage(new Message(content, recipient));
                        System.out.println(t("m.msg.sent") + recipient.getName() + ".");
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "4": {
                    List<Student> students = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Student) students.add((Student) u);
                    if (students.isEmpty()) { System.out.println(t("no.students")); break; }
                    for (int i = 0; i < students.size(); i++) System.out.println((i+1) + ". " + students.get(i).getName());
                    System.out.print(t("p.sel.student"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= students.size()) { System.out.println(t("invalid.sel")); break; }
                        System.out.print(t("p.complaint.text")); String text = scanner.nextLine().trim();
                        System.out.println(t("p.urgency")); System.out.print(t("choice"));
                        int u = Integer.parseInt(scanner.nextLine().trim());
                        UrgencyLevel urgency = u == 1 ? UrgencyLevel.LOW : u == 3 ? UrgencyLevel.HIGH : UrgencyLevel.MEDIUM;
                        teacher.sendComplaint(students.get(idx), text, urgency);
                        Complaint c = teacher.getComplaints().get(teacher.getComplaints().size() - 1);
                        allComplaints.add(c);
                        System.out.println(t("m.complaint.sent") + c.getStatus() + t("m.urgency") + urgency);
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "5": researchMenu(teacher); break;
                case "6": teacher.logout(); return;
                default: System.out.println(t("invalid.choice"));
            }
        }
    }

    static void researchMenu(Researcher researcher) {
        while (true) {
            System.out.println("\n" + t("research.title"));
            System.out.println(t("research.1")); System.out.println(t("research.2"));
            System.out.println(t("research.3")); System.out.println(t("research.4"));
            System.out.println(t("research.5")); System.out.println(t("research.6"));
            System.out.println(t("research.7"));
            System.out.print(t("choice")); String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    String authorName = (researcher instanceof User) ? ((User) researcher).getName() : "Unknown";
                    try {
                        System.out.print(t("p.title"));   String title   = scanner.nextLine().trim();
                        System.out.print(t("p.journal")); String journal = scanner.nextLine().trim();
                        System.out.print(t("p.pages"));   int pages = Integer.parseInt(scanner.nextLine().trim());
                        ResearchPaper paper = new ResearchPaper(title, journal, new Date(), authorName, pages);
                        paper.setCitations(0);
                        if (researcher instanceof Teacher) ((Teacher) researcher).addPaper(paper);
                        else if (researcher instanceof GraduateStudent) ((GraduateStudent) researcher).addPaper(paper);
                        researcher.publishPaper();
                        System.out.println(t("m.paper.added") + title);
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "2": {
                    List<ResearchPaper> papers = null;
                    if (researcher instanceof Teacher) papers = ((Teacher) researcher).getPapers();
                    else if (researcher instanceof GraduateStudent) papers = ((GraduateStudent) researcher).getPapers();
                    if (papers == null || papers.isEmpty()) { System.out.println(t("no.papers")); break; }
                    for (ResearchPaper p : papers)
                        System.out.println("  " + p.getTitle() + " | " + p.getJournal() + " | Citations: " + p.getCitations());
                    break;
                }
                case "3": System.out.println(t("m.hindex") + researcher.calculateHIndex()); break;
                case "4": {
                    System.out.println(t("m.sort")); System.out.print(t("choice")); String sort = scanner.nextLine().trim();
                    switch (sort) {
                        case "1": researcher.printPapers(new CitationComparator()); break;
                        case "2": researcher.printPapers(new DateComparator()); break;
                        case "3": researcher.printPapers(new PageCountComparator()); break;
                        default: System.out.println(t("m.sort.invalid")); break;
                    }
                    break;
                }
                case "5": researcher.joinResearchProject(); break;
                case "6": {
                    List<ResearchPaper> papers = null;
                    if (researcher instanceof Teacher) papers = ((Teacher) researcher).getPapers();
                    else if (researcher instanceof GraduateStudent) papers = ((GraduateStudent) researcher).getPapers();
                    if (papers == null || papers.isEmpty()) { System.out.println(t("no.papers.avail")); break; }
                    for (int i = 0; i < papers.size(); i++)
                        System.out.println((i+1) + ". " + papers.get(i).getTitle() + " | Citations: " + papers.get(i).getCitations());
                    System.out.print(t("p.sel.paper"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= papers.size()) { System.out.println(t("invalid.sel")); break; }
                        System.out.print(t("p.citations")); int count = Integer.parseInt(scanner.nextLine().trim());
                        papers.get(idx).addCitation(count);
                        System.out.println(t("m.citations.upd") + papers.get(idx).getCitations());
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "7": return;
                default: System.out.println(t("invalid.choice"));
            }
        }
    }

    static void managerMenu(Manager mgr) {
        while (true) {
            System.out.println("\n" + t("manager.title"));
            System.out.println(t("manager.1")); System.out.println(t("manager.2"));
            System.out.println(t("manager.3")); System.out.println(t("manager.4"));
            System.out.println(t("manager.5")); System.out.println(t("manager.6"));
            System.out.println(t("manager.7")); System.out.println(t("manager.8"));
            System.out.println(t("manager.9"));
            System.out.print(t("choice")); String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Teacher) teachers.add((Teacher) u);
                    if (teachers.isEmpty()) { System.out.println(t("no.teachers")); break; }
                    for (int i = 0; i < teachers.size(); i++) System.out.println((i+1) + ". " + teachers.get(i).getName());
                    System.out.print(t("p.sel.teacher"));
                    try {
                        int tIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (tIdx < 0 || tIdx >= teachers.size()) { System.out.println(t("invalid.sel")); break; }
                        for (int i = 0; i < allCourses.size(); i++) System.out.println((i+1) + ". " + allCourses.get(i).getTitle());
                        System.out.print(t("p.sel.course"));
                        int cIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (cIdx < 0 || cIdx >= allCourses.size()) { System.out.println(t("invalid.sel")); break; }
                        mgr.assignCourse(allCourses.get(cIdx), teachers.get(tIdx));
                        System.out.println(t("m.course.assigned") + teachers.get(tIdx).getName() + ".");
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "2": {
                    List<Student> students = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof Student) students.add((Student) u);
                    if (students.isEmpty()) { System.out.println(t("no.students")); break; }
                    for (int i = 0; i < students.size(); i++) System.out.println((i+1) + ". " + students.get(i).getName());
                    System.out.print(t("p.sel.student"));
                    try {
                        int sIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (sIdx < 0 || sIdx >= students.size()) { System.out.println(t("invalid.sel")); break; }
                        for (int i = 0; i < allCourses.size(); i++) System.out.println((i+1) + ". " + allCourses.get(i).getTitle());
                        System.out.print(t("p.sel.course"));
                        int cIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (cIdx < 0 || cIdx >= allCourses.size()) { System.out.println(t("invalid.sel")); break; }
                        Registration reg = new Registration(students.get(sIdx), allCourses.get(cIdx));
                        try {
                            mgr.approveRegistration(reg);
                            System.out.println(t("m.reg.status") + reg.getStatus());
                        } catch (CreditLimitExceededException | RetakeLimitExceededException e) { System.out.println("[ERROR] " + e.getMessage()); }
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "3": mgr.createReport(); break;
                case "4": {
                    System.out.print(t("p.title"));   String title   = scanner.nextLine().trim();
                    System.out.print(t("p.content")); String content = scanner.nextLine().trim();
                    News news = new News(title, content); mgr.manageNews(news); allNews.add(news); break;
                }
                case "5":
                    if (allNews.isEmpty()) System.out.println(t("no.news"));
                    else allNews.forEach(n -> System.out.println("[" + n.getDate() + "] " + n.getTitle() + ": " + n.getContent())); break;
                case "6": {
                    if (allComplaints.isEmpty()) { System.out.println(t("no.complaints")); break; }
                    for (int i = 0; i < allComplaints.size(); i++) {
                        Complaint c = allComplaints.get(i);
                        System.out.println((i+1) + ". " + t("m.from") + c.getTeacher().getName() +
                                t("m.about") + c.getStudent().getName() +
                                t("m.urgency") + c.getUrgency() + t("m.status") + c.getStatus());
                        System.out.println(t("m.text") + c.getText());
                    } break;
                }
                case "7": {
                    if (allComplaints.isEmpty()) { System.out.println(t("no.complaints")); break; }
                    for (int i = 0; i < allComplaints.size(); i++) {
                        Complaint c = allComplaints.get(i);
                        System.out.println((i+1) + ". " + c.getTeacher().getName() + " → " + c.getStudent().getName() + " | " + c.getUrgency() + " | " + c.getStatus());
                    }
                    System.out.print(t("p.sel.complaint"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= allComplaints.size()) { System.out.println(t("invalid.sel")); break; }
                        mgr.processComplaint(allComplaints.get(idx));
                        System.out.println(t("m.complaint.upd") + allComplaints.get(idx).getStatus());
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "8": {
                    List<TechSupportSpecialist> techs = new ArrayList<>();
                    for (User u : allUsers) if (u instanceof TechSupportSpecialist) techs.add((TechSupportSpecialist) u);
                    if (techs.isEmpty()) { System.out.println(t("no.tech")); break; }
                    for (int i = 0; i < techs.size(); i++) System.out.println((i+1) + ". " + techs.get(i).getName());
                    System.out.print(t("p.sel.specialist"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= techs.size()) { System.out.println(t("invalid.sel")); break; }
                        System.out.print(t("p.request")); String desc = scanner.nextLine().trim();
                        Request req = new Request(desc); techs.get(idx).addRequest(req);
                        System.out.println(t("m.req.sent") + techs.get(idx).getName() + ". Status: " + req.getStatus());
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "9": mgr.logout(); return;
                default: System.out.println(t("invalid.choice"));
            }
        }
    }

    static void techMenu(TechSupportSpecialist tech) {
        while (true) {
            System.out.println("\n" + t("tech.title"));
            System.out.println(t("tech.1")); System.out.println(t("tech.2"));
            System.out.println(t("tech.3")); System.out.println(t("tech.4"));
            System.out.print(t("choice")); String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": {
                    List<Request> reqs = tech.viewRequests();
                    if (reqs.isEmpty()) System.out.println(t("no.requests"));
                    else for (int i = 0; i < reqs.size(); i++) System.out.println((i+1) + ". " + reqs.get(i)); break;
                }
                case "2": {
                    List<Request> reqs = tech.viewRequests();
                    if (reqs.isEmpty()) { System.out.println(t("no.requests")); break; }
                    for (int i = 0; i < reqs.size(); i++) System.out.println((i+1) + ". " + reqs.get(i));
                    System.out.print(t("choice"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < reqs.size()) { reqs.get(idx).view(); tech.acceptRequest(reqs.get(idx)); System.out.println(t("m.accepted") + reqs.get(idx).getStatus()); }
                        else System.out.println(t("invalid.sel"));
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "3": {
                    List<Request> reqs = tech.viewRequests();
                    if (reqs.isEmpty()) { System.out.println(t("no.requests")); break; }
                    for (int i = 0; i < reqs.size(); i++) System.out.println((i+1) + ". " + reqs.get(i));
                    System.out.print(t("choice"));
                    try {
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < reqs.size()) { reqs.get(idx).view(); tech.rejectRequest(reqs.get(idx)); System.out.println(t("m.rejected") + reqs.get(idx).getStatus()); }
                        else System.out.println(t("invalid.sel"));
                    } catch (NumberFormatException e) { System.out.println(t("invalid.input")); }
                    break;
                }
                case "4": tech.logout(); return;
                default: System.out.println(t("invalid.choice"));
            }
        }
    }
}
