package com.swiftpot.swiftalertmain.db.model;

import com.swiftpot.swiftalertmain.helpers.DeliveryStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 2:16 PM
 */
@Document(collection = "MessagesDetailedReportDoc")
public class MessagesDetailedReportDoc {

    @Id
    String id;

    String dateCreated;

    String messageId;

    String recipientNum;

    String deliveryStatus;

    String groupId;

    String senderId;

    String userName;


    public MessagesDetailedReportDoc() {
    }

    public MessagesDetailedReportDoc(String dateCreated, String messageId, String recipientNum, String senderId,String groupId,String userName) {
        this.dateCreated = dateCreated;
        this.messageId = messageId;
        this.recipientNum = recipientNum;
        this.senderId = senderId;
        this.groupId = groupId;
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

    public String getRecipientNum() {
        return recipientNum;
    }

    public void setRecipientNum(String recipientNum) {
        this.recipientNum = recipientNum;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
