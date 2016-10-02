package com.swiftpot.swiftalertmain.models;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 2:51 PM
 */
public class BulkMessagesRequest {

    String userName;

    String groupId;

    String senderId;

    String message;

    public BulkMessagesRequest() {
    }

    public BulkMessagesRequest(String userName, String groupId, String senderId, String message) {
        this.userName = userName;
        this.groupId = groupId;
        this.senderId = senderId;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
}
