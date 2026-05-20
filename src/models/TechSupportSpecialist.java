package models;

import enums.Language;
import enums.RequestStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TechSupportSpecialist extends Employee {
    private List<Request> requests;

    public TechSupportSpecialist(int id, String username, String password, String name, Language language, String employeeID, double salary, Date hireDate) {
        super(id, username, password, name, language, employeeID, salary, hireDate);
        this.requests = new ArrayList<>();
    }

    public List<Request> viewRequests() {
        return requests;
    }

    public void acceptRequest(Request request) {
        if (request == null) return;
        request.setStatus(RequestStatus.ACCEPTED);
        if (!requests.contains(request)) requests.add(request);
    }

    public void rejectRequest(Request request) {
        if (request == null) return;
        request.setStatus(RequestStatus.REJECTED);
        if (!requests.contains(request)) requests.add(request);
    }

    public void addRequest(Request request) {
        if (request != null && !requests.contains(request)) requests.add(request);
    }
}
