package com.swiftpot.swiftalertmain.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         13-Oct-16 @ 7:54 AM
 */
@Document(collection = "MessageContentDoc")
public class MessageContentsDoc {
    @Id
    String id;

    String messageId;

    String message;

    String dateCreated;

    public MessageContentsDoc() {
    }

    public MessageContentsDoc(String messageId, String message,String dateCreated) {
        this.messageId = messageId;
        this.message = message;
        this.dateCreated = dateCreated;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
