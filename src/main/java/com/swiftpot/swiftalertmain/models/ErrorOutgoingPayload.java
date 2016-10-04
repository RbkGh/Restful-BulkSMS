package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 9:27 PM
 */
public class ErrorOutgoingPayload extends OutgoingPayload {
    public ErrorOutgoingPayload() {
    }

    public ErrorOutgoingPayload(String message) {
        this.status = "11";
        this.message = message;
        this.responseObject = null;
    }
    public ErrorOutgoingPayload(Object responseObject) {
        this.status = "11";
        this.message = "Error";
        this.responseObject = responseObject;
    }

    public ErrorOutgoingPayload(String message, Object responseObject) {
        super(message, responseObject);
        this.status="11";

    }
}
