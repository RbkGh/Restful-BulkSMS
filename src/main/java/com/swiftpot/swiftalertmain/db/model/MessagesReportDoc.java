package com.swiftpot.swiftalertmain.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 7:08 PM
 */
@Document(collection="MessagesReportDoc")
public class MessagesReportDoc {
    @Id
    String id;

    String userName;

    String dateCreated;

    String messageId;

    String groupName;

    String groupId;

    String noOfMessages;

    int creditBefore;

    int creditAfter;


    public MessagesReportDoc() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNoOfMessages() {
        return noOfMessages;
    }

    public void setNoOfMessages(String noOfMessages) {
        this.noOfMessages = noOfMessages;
    }

    public int getCreditBefore() {
        return creditBefore;
    }

    public void setCreditBefore(int creditBefore) {
        this.creditBefore = creditBefore;
    }

    public int getCreditAfter() {
        return creditAfter;
    }

    public void setCreditAfter(int creditAfter) {
        this.creditAfter = creditAfter;
    }


}
