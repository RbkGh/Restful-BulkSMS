package com.swiftpot.swiftalertmain.businesslogic;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import com.swiftpot.swiftalertmain.db.model.GroupsDoc;
import com.swiftpot.swiftalertmain.models.BulkGroupContactsCreationRequest;
import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SuccessfulOutgoingPayload;
import com.swiftpot.swiftalertmain.repositories.GroupContactsDocRepository;
import com.swiftpot.swiftalertmain.repositories.GroupsDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         03-Oct-16 @ 2:13 AM
 */
@Service
public class GroupContactsLogic {


    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    Gson g;
    @Autowired
    GroupContactsDocRepository groupContactsDocRepository;
    @Autowired
    GroupsDocRepository groupsDocRepository;

    public OutgoingPayload createOneGroupContact(GroupContactsDoc groupContactsDoc) {
        log.info("Create One GroupContact Request : {}", groupContactsDoc);

        OutgoingPayload outgoingPayload;

        try {

            if (isGroupIdPresentInGroupsDocument(groupContactsDoc.getGroupId())) {
                if (isContactPhoneNumberAlreadyPresent(groupContactsDoc.getContactPhoneNum())) {
                    outgoingPayload = new ErrorOutgoingPayload("PhoneNumber of Contact Already Exists");
                } else {
                    GroupContactsDoc groupsDocFinal = groupContactsDocRepository.save(groupContactsDoc);
                    outgoingPayload = new SuccessfulOutgoingPayload("Created Successfully", groupsDocFinal);
                }
            } else {
                outgoingPayload = new ErrorOutgoingPayload("GroupId does not exist");
            }


        } catch (Exception e) {
            log.info("Exception cause : " + e.getCause().getMessage());
            outgoingPayload = new ErrorOutgoingPayload("Bro,I couldn't save a Group with Id of {}.Boy,I'm stoned!!:P =D", groupContactsDoc.getId());
        }
        return outgoingPayload;
    }

    public OutgoingPayload createMultipleGroupContacts(BulkGroupContactsCreationRequest bulkGroupContactsCreationRequest) {
        log.info("Create Multiple GroupContact Request aka Upload Multiple Contacts  : {}", g.toJson(bulkGroupContactsCreationRequest));
        OutgoingPayload outgoingPayload;

        String groupId = bulkGroupContactsCreationRequest.getGroupId();
        String userName = bulkGroupContactsCreationRequest.getGroupId();
        List<GroupContactsDoc> iterableGroupContacts = bulkGroupContactsCreationRequest.getContactsList();
        log.info("Raw contacts List number before duplicate numbers removed = " + iterableGroupContacts.size());

        List<GroupContactsDoc> newlySetGroupIdAndUserNameContactsDoc = new ArrayList<>(0);

        //set groupId and userName for each since user will not set GroupId with each contact List element
        for (GroupContactsDoc groupContactsDocElement : iterableGroupContacts) {
            groupContactsDocElement.setGroupId(groupId);
            groupContactsDocElement.setUserName(userName);
            newlySetGroupIdAndUserNameContactsDoc.add(groupContactsDocElement);
            /**
             * add first element by default
             */


        }
        log.info("Newly sorted list,with duplicate phoneNumbers removed = " + newlySetGroupIdAndUserNameContactsDoc.size());

        //sort and remove duplicates
        List<GroupContactsDoc> incomingGroupContactsListNotFromDB = newlySetGroupIdAndUserNameContactsDoc;

        List<GroupContactsDoc> contactsListFromDB = getGroupContactsListFromDbUsingGroupId(groupId);
        int noOfSavedContacts = 0;
        //if GroupContactsDoc list from db number !=0,then try to remove duplicates if any,before saving
        if (contactsListFromDB.size() != 0) {
            List<GroupContactsDoc> groupContactsDocListFinalToBeSaved =
                    removeDuplicatesFromIncomingListByCheckingDBList(incomingGroupContactsListNotFromDB, contactsListFromDB, groupId);
            log.info("no of final list to save in db = " + groupContactsDocListFinalToBeSaved.size());
            noOfSavedContacts = noOfContactsCreatedSuccessfully(groupContactsDocListFinalToBeSaved);
        }

        if (noOfSavedContacts == 0) {
            outgoingPayload = new SuccessfulOutgoingPayload("Nothing was saved because there are duplicate numbers alredy", null);
        } else {
            outgoingPayload = new SuccessfulOutgoingPayload(noOfSavedContacts + " saved Successfully");
        }


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


        List<GroupContactsDoc> groupContactsDocsSavedList = groupContactsDocRepository.save(groupContactsDocList);


        return groupContactsDocsSavedList.size();
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

    public OutgoingPayload getAllGroupContactsByGroupId(String groupId) {
        log.info("getAll Contacts By GroupId {}" + groupId);
        OutgoingPayload outgoingPayload;

        List<GroupContactsDoc> groupContactsDocs = getGroupContactsListFromDbUsingGroupId(groupId);
        if (!(groupContactsDocs.isEmpty())) {
            outgoingPayload = new SuccessfulOutgoingPayload(groupContactsDocs);
        } else {
            outgoingPayload = new ErrorOutgoingPayload("GroupId does not exist");
        }

        return outgoingPayload;
    }

    List<GroupContactsDoc> getGroupContactsListFromDbUsingGroupId(String groupId) {
        return groupContactsDocRepository.findByGroupId(groupId);
    }

    boolean isGroupIdPresentInGroupsDocument(String groupId) {
        boolean isGroupIdPresentInGroupsDocument = false;
        GroupsDoc groupsDoc = groupsDocRepository.findByGroupId(groupId);
        if (!(groupsDoc == null)) {
            isGroupIdPresentInGroupsDocument = true;
        }

        return isGroupIdPresentInGroupsDocument;
    }

    boolean isContactPhoneNumberAlreadyPresent(String contactPhoneNum) {
        boolean isContactPhoneNumberAlreadyPresent = false;
        GroupContactsDoc groupContactsDoc = groupContactsDocRepository.findByContactPhoneNum(contactPhoneNum);
        if (!(groupContactsDoc == null)) {
            isContactPhoneNumberAlreadyPresent = true;
        }
        return isContactPhoneNumberAlreadyPresent;
    }
}
