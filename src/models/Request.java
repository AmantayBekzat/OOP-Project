package models;

import enums.RequestStatus;

public class Request {
    private String description;
    private RequestStatus status;

    public Request() {
        this.status = RequestStatus.NEW;
    }

    public Request(String description) {
        this.description = description;
        this.status = RequestStatus.NEW;
    }

    public void accept() {
        this.status = RequestStatus.ACCEPTED;
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
}
