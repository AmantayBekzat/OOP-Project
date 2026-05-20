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

    public void view() {
        this.status = RequestStatus.VIEWED;
    }

    public void accept() {
        if (status == RequestStatus.VIEWED) this.status = RequestStatus.ACCEPTED;
    }

    public void reject() {
        if (status == RequestStatus.VIEWED) this.status = RequestStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "Request{description='" + description + "', status=" + status + "}";
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
}
