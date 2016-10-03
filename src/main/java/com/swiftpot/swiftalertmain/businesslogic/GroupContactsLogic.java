package com.swiftpot.swiftalertmain.businesslogic;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import com.swiftpot.swiftalertmain.models.BulkGroupContactsCreationRequest;
import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SuccessfulOutgoingPayload;
import com.swiftpot.swiftalertmain.repositories.GroupContactsDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         03-Oct-16 @ 2:13 AM
 */
public class GroupContactsLogic {


    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    Gson g;
    @Autowired
    GroupContactsDocRepository groupContactsDocRepository;

    public OutgoingPayload createOneGroupContact(GroupContactsDoc groupContactsDoc) {
        log.info("Create One GroupContact Request : {}", groupContactsDoc);

        OutgoingPayload outgoingPayload;

        try {
            GroupContactsDoc groupsDocFinal = groupContactsDocRepository.save(groupContactsDoc);
            outgoingPayload = new SuccessfulOutgoingPayload("Created Successfully", groupsDocFinal);

        } catch (Exception e) {
            log.info("Exception cause : " + e.getCause().getMessage());
            outgoingPayload = new ErrorOutgoingPayload("Bro,I couldn't save a Group with Id of {}.Boy,I'm stoned!!:P =D", groupContactsDoc.getId());
        }
        return outgoingPayload;
    }

    public OutgoingPayload createMultipleGroupContacts(BulkGroupContactsCreationRequest bulkGroupContactsCreationRequest) {
        log.info("Create Multiple GroupContact Request aka Upload Multiple Contacts  : {}", g.toJson(bulkGroupContactsCreationRequest));


        OutgoingPayload outgoingPayload = new OutgoingPayload();

        String groupId = bulkGroupContactsCreationRequest.getGroupId();

        List<GroupContactsDoc> newlySetGroupIdContactsDoc = new ArrayList<>(0);

        //set groupId for each since user will not set GroupId with each contact List element
        for (GroupContactsDoc groupContactsDocElement : bulkGroupContactsCreationRequest.getContactsList()) {
            groupContactsDocElement.setGroupId(groupId);
            newlySetGroupIdContactsDoc.add(groupContactsDocElement);
        }

        //sort and remove duplicates
        List<GroupContactsDoc> incomingGroupContactsListNotFromDB = newlySetGroupIdContactsDoc;

        List<GroupContactsDoc> contactsListFromDB = getGroupContactsListUsingGroupId(groupId);

        List<GroupContactsDoc> groupContactsDocListFinalToBeSaved =
                removeDuplicatesFromIncomingListByCheckingDBList(incomingGroupContactsListNotFromDB, contactsListFromDB, groupId);

        int noOfSavedContacts = noOfContactsCreatedSuccessfully(groupContactsDocListFinalToBeSaved);

        outgoingPayload = new SuccessfulOutgoingPayload(noOfSavedContacts + " saved Successfully");

        return outgoingPayload;
    }

    public OutgoingPayload deleteSingleGroupContactById(String id) {
        log.info("Delete Single GroupContact Request   : {}", id);
        OutgoingPayload outgoingPayload;

        try {
            groupContactsDocRepository.delete(id);
            outgoingPayload = new OutgoingPayload("Deleted Successfully", null);
        } catch (Exception e) {
            outgoingPayload = new ErrorOutgoingPayload("Could not delete");
        }
        return outgoingPayload;
    }

    public OutgoingPayload updateSingleGroupContact(GroupContactsDoc groupContactsDoc) {
        log.info("Update One SingleGroupContact Request : {}", g.toJson(groupContactsDoc));
        OutgoingPayload outgoingPayload;

        try {
            boolean isDocumentPresent = groupContactsDocRepository.exists(groupContactsDoc.getId());
            if (isDocumentPresent) {
                GroupContactsDoc groupsContactDocFinal = groupContactsDocRepository.save(groupContactsDoc);
                outgoingPayload = new SuccessfulOutgoingPayload(groupsContactDocFinal);
            } else {
                outgoingPayload = new ErrorOutgoingPayload("Entity does not exist,bro!!:)");
            }
        } catch (Exception e) {
            log.info("Exception cause : " + e.getCause().getMessage());
            outgoingPayload = new ErrorOutgoingPayload("Could Not Update entity with id : {}.I don't think id exists,bruh :)", groupContactsDoc.getId());
        }
        return outgoingPayload;
    }

    int noOfContactsCreatedSuccessfully(List<GroupContactsDoc> groupContactsDocList) {
        int totalNoOfContactsCreated = 0;

        for (GroupContactsDoc groupContactsDoc : groupContactsDocList) {
            try {
                groupContactsDocRepository.save(groupContactsDoc);
                totalNoOfContactsCreated++;
            } catch (Exception e) {
                //do nothing,don't add or deduct from totalNoOf saves
            }
        }

        return totalNoOfContactsCreated;
    }

    List<GroupContactsDoc> removeDuplicatesFromIncomingListByCheckingDBList(List<GroupContactsDoc> incomingGroupContactsListNotFromDB,
                                                                            List<GroupContactsDoc> contactsListFromDB,
                                                                            String groupId) {
        List<GroupContactsDoc> finalContactsList = new ArrayList<>(0);
        for (GroupContactsDoc groupContactsDocNotFromDB : incomingGroupContactsListNotFromDB) {
            for (GroupContactsDoc contactDocFromDB : contactsListFromDB) {
                /**
                 * if it does not match any,then add to finalContactsList,if it matches,just don't add to finalList
                 */
                if (!(groupContactsDocNotFromDB.getContactPhoneNum() == contactDocFromDB.getContactPhoneNum())) {
                    finalContactsList.add(groupContactsDocNotFromDB);
                }
            }
        }


        return finalContactsList;
    }

    List<GroupContactsDoc> getGroupContactsListUsingGroupId(String groupId) {
        return groupContactsDocRepository.findByGroupId(groupId);
    }
}
