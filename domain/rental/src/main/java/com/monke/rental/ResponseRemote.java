package com.monke.rental;

public class ResponseRemote {
    public String responseId;
    public String userId;
    public String rentalId;
    public long dateOfResponse;
    public String status;

    public ResponseRemote() {

    }

    public ResponseRemote(Response response) {
        this.responseId = response.getResponseId();
        this.userId = response.getUserId();
        this.rentalId = response.getRentalId();
        this.dateOfResponse = response.getDateOfResponse();
        this.status = response.getStatus().name();
    }

    public Response toDomain() {
        return new Response(
                responseId,
                userId,
                rentalId,
                dateOfResponse,
                Response.Status.valueOf(status)
        );
    }
}
