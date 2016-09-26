package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 8:46 PM
 */
public class OutgoingPayload {

    String status;

    String message;

    Object responseObject;

    public OutgoingPayload() {
    }

    public OutgoingPayload(String status, String message, Object responseObject) {
        this.status = status;
        this.message = message;
        this.responseObject = responseObject;
    }

    public OutgoingPayload(String message, Object responseObject) {
        this.message = message;
        this.responseObject = responseObject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
