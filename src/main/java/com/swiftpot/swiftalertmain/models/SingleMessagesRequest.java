package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 9:09 PM
 */
public class SingleMessagesRequest {

    String userName;

    String senderId;

    String message;

    String recieverNum;

    public SingleMessagesRequest() {
    }

    /**
     *
     * @param userName
     * @param senderId
     * @param message
     * @param recieverNum
     */
    public SingleMessagesRequest(String userName, String senderId, String message, String recieverNum) {
        this.userName = userName;
        this.senderId = senderId;
        this.message = message;
        this.recieverNum = recieverNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecieverNum() {
        return recieverNum;
    }

    public void setRecieverNum(String recieverNum) {
        this.recieverNum = recieverNum;
    }
}
