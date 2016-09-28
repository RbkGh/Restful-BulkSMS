package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 9:20 PM
 */
public class SuccessfulOutgoingPayload extends OutgoingPayload {


    public SuccessfulOutgoingPayload() {
    }

    public SuccessfulOutgoingPayload(Object responseObject) {
        this.status = "00";
        this.message = "Successful";
        this.responseObject = responseObject;
    }
}
