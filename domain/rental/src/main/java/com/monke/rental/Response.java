package com.monke.rental;

import java.util.Objects;

public class Response {
    private String responseId;
    private String userId;
    private String rentalId;
    private long dateOfResponse;
    private Status status;

    public Response(String responseId, String userId, String rentalId,
                    long dateOfResponse, Status status) {
        this.responseId = responseId;
        this.userId = userId;
        this.rentalId = rentalId;
        this.dateOfResponse = dateOfResponse;
        this.status = status;
    }

    public Response(Response response) {
        this.responseId = response.responseId;
        this.userId = response.userId;
        this.rentalId = response.rentalId;
        this.dateOfResponse = response.dateOfResponse;
        this.status = response.status;
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

    public long getDateOfResponse() {
        return dateOfResponse;
    }

    public void setDateOfResponse(long dateOfResponse) {
        this.dateOfResponse = dateOfResponse;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        LIKED, DISLIKED, HANGING, FLATMATE
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return dateOfResponse == response.dateOfResponse && Objects.equals(responseId, response.responseId) && Objects.equals(userId, response.userId) && Objects.equals(rentalId, response.rentalId) && status == response.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseId, userId, rentalId, dateOfResponse, status);
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseId='" + responseId + '\'' +
                ", userId='" + userId + '\'' +
                ", rentalId='" + rentalId + '\'' +
                ", dateOfResponse=" + dateOfResponse +
                ", status=" + status +
                '}';
    }
}
