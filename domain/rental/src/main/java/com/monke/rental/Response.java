package com.monke.rental;

import java.util.Objects;

public class Response {
    private String responseId;
    private String userId;
    private String dateOfResponse;
    private Status status;

    public Response(String responseId, String userId, String dateOfResponse, Status status) {
        this.responseId = responseId;
        this.userId = userId;
        this.dateOfResponse = dateOfResponse;
        this.status = status;
    }


    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateOfResponse() {
        return dateOfResponse;
    }

    public void setDateOfResponse(String dateOfResponse) {
        this.dateOfResponse = dateOfResponse;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        POSITIVE, NEGATIVE, HANGING
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(responseId, response.responseId) && Objects.equals(userId, response.userId) && Objects.equals(dateOfResponse, response.dateOfResponse) && status == response.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseId, userId, dateOfResponse, status);
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseId='" + responseId + '\'' +
                ", userId='" + userId + '\'' +
                ", dateOfResponse='" + dateOfResponse + '\'' +
                ", status=" + status +
                '}';
    }
}
