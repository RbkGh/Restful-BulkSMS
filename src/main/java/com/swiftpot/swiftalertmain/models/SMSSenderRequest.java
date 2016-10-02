package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 2:39 PM
 */
public class SMSSenderRequest {
    private String senderId;

    private String recipientPhoneNum;

    private String message;

    public SMSSenderRequest(){}

    public SMSSenderRequest(String senderId,String message,String recipientPhoneNum) {
        this.senderId = senderId;
        this.recipientPhoneNum = recipientPhoneNum;
        this.message = message;
    }

    public String getRecipientPhoneNum() {
        return recipientPhoneNum;
    }

    public void setRecipientPhoneNum(String recipientPhoneNum) {
        this.recipientPhoneNum = recipientPhoneNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
