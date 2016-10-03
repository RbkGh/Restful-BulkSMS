package com.swiftpot.swiftalertmain.models;

import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;

import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         03-Oct-16 @ 2:44 AM
 */
public class BulkGroupContactsCreationRequest {

    String groupId;

    List<GroupContactsDoc> contactsList;

    public BulkGroupContactsCreationRequest(String groupId, List<GroupContactsDoc> contactsList) {
        this.groupId = groupId;
        this.contactsList = contactsList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<GroupContactsDoc> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<GroupContactsDoc> contactsList) {
        this.contactsList = contactsList;
    }
}
