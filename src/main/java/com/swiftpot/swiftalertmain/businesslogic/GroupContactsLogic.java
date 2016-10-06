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
                if (isContactPhoneNumberAndUsernameAlreadyPresent(groupContactsDoc.getContactPhoneNum(), groupContactsDoc.getUserName())) {
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
        String userName = bulkGroupContactsCreationRequest.getUserName();
        List<GroupContactsDoc> iterableGroupContacts = bulkGroupContactsCreationRequest.getContactsList();
        log.info("Raw contacts List number before duplicate numbers removed = " + iterableGroupContacts.size());

        List<GroupContactsDoc> newlySetGroupIdAndUserNameContactsDoc = new ArrayList<>(0);

        //set groupId and userName for each since user will not set GroupId with each contact List element
        for (GroupContactsDoc groupContactsDocElement : iterableGroupContacts) {
            groupContactsDocElement.setGroupId(groupId);
            groupContactsDocElement.setUserName(userName);
            newlySetGroupIdAndUserNameContactsDoc.add(groupContactsDocElement);
        }
        log.info("Newly sorted list,with set groupId and Username = " + newlySetGroupIdAndUserNameContactsDoc.size());
        //sort and remove duplicates Initial
        List<GroupContactsDoc> removeInitialDuplicates = removeDuplicateContactsInitial(newlySetGroupIdAndUserNameContactsDoc);
        log.info("Newly sorted List with Duplicates Removed Stage1 = " + removeInitialDuplicates.size() + " contacts");

        List<GroupContactsDoc> groupContactsDocListFinallySaved =
                removeDuplicatesFromIncomingListByCheckingDBListAndSave(removeInitialDuplicates);
        int noOfSavedContacts = groupContactsDocListFinallySaved.size();
        log.info("no of final list saved in db = " + noOfSavedContacts);

        if (noOfSavedContacts == 0) {
            outgoingPayload = new SuccessfulOutgoingPayload("Nothing was saved because the contacts exist already", null);
        } else {
            outgoingPayload = new SuccessfulOutgoingPayload(noOfSavedContacts + " saved Successfully");
        }


        return outgoingPayload;
    }

    public OutgoingPayload deleteSingleGroupContactById(String id) {
        log.info("Delete Single GroupContact Request   : {}", id);
        OutgoingPayload outgoingPayload;

        try {
            if (!groupContactsDocRepository.exists(id)) {
                outgoingPayload = new ErrorOutgoingPayload("Id does not exist");
            } else {
                groupContactsDocRepository.delete(id);
                outgoingPayload = new SuccessfulOutgoingPayload("Deleted Successfully", null);
            }

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

                //Check to make sure no duplicate phoneNumber is being inserted when updating,
                //Find entity to update using its id since any other value could be an update but the id will remain same
                GroupContactsDoc groupContactsDocFromDb = groupContactsDocRepository.findOne(groupContactsDoc.getId());

                boolean isPhoneNumbersSame = (groupContactsDoc.getContactPhoneNum().trim().equalsIgnoreCase(groupContactsDocFromDb.getContactPhoneNum().trim()));
                log.info("incomingPhoneNumber ="+groupContactsDoc.getContactPhoneNum().trim());
                log.info("DBPhoneNumber ="+groupContactsDocFromDb.getContactPhoneNum().trim());
                log.info("isPhoneNumbersSame ="+isPhoneNumbersSame);
                if (!(isPhoneNumbersSame)) {
                    log.info("new Phone Numbers are not the same");
                    boolean isGroupIdAndUserNamePresentForNewPhoneNumberToBeUpdated = isContactPhoneNumberAndUsernameAlreadyPresent(groupContactsDoc.getContactPhoneNum(), groupContactsDoc.getUserName());
                    log.info("isGroupIdAndUserNamePresentForNewPhoneNumberToBeUpdated = "+isGroupIdAndUserNamePresentForNewPhoneNumberToBeUpdated);
                    if(isGroupIdAndUserNamePresentForNewPhoneNumberToBeUpdated){
                        outgoingPayload = new ErrorOutgoingPayload("Could Not Update Because The New PhoneNumber Exists Already ");
                    }else {
                        GroupContactsDoc groupsContactDocFinal = groupContactsDocRepository.save(groupContactsDoc);
                        outgoingPayload = new SuccessfulOutgoingPayload(groupsContactDocFinal);
                    }
                } else {

                    //Once phoneNumbers are same,no need to check if new phoneNumber is existing in DB
                    GroupContactsDoc groupsContactDocFinal = groupContactsDocRepository.save(groupContactsDoc);
                    outgoingPayload = new SuccessfulOutgoingPayload(groupsContactDocFinal);
                }

            } else {
                outgoingPayload = new ErrorOutgoingPayload("Entity does not exist,bro!!:)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            outgoingPayload = new ErrorOutgoingPayload("Could Not Update entity with id : {}.I don't think id exists,bruh :)", groupContactsDoc.getId());
        }
        return outgoingPayload;
    }

    private ArrayList<GroupContactsDoc> removeDuplicateContactsInitial(List<GroupContactsDoc> contacts) {
        log.info("removed duplicates before executing removeDuplicateContactsBlock::: " + g.toJson(contacts) + " length:::" + contacts.size());
        LinkedHashSet<String> noDuplicateLinkedHashSet = new LinkedHashSet<>(0);
        ArrayList<GroupContactsDoc> noDuplicateArrayList = new ArrayList<>(0);
        for (GroupContactsDoc singleContact : contacts) {
            String phoneNumber = singleContact.getContactPhoneNum();

            if (noDuplicateLinkedHashSet.add(phoneNumber) == false) {
                //duplicate culprit,hence do nothing

            } else {
                noDuplicateArrayList.add(singleContact);
            }


        }

        log.info("removed duplicates after executing removeDuplicateContactsBlock::: " + g.toJson(noDuplicateArrayList) + " length:::" + noDuplicateArrayList.size());


        return noDuplicateArrayList;

    }

    List<GroupContactsDoc> removeDuplicatesFromIncomingListByCheckingDBListAndSave(List<GroupContactsDoc> incomingGroupContactsListNotFromDB
    ) {
        List<GroupContactsDoc> finalContactsList = new ArrayList<>(0);
        for (GroupContactsDoc groupContactsDocNotFromDB : incomingGroupContactsListNotFromDB) {

            String contactPhoneNumber = groupContactsDocNotFromDB.getContactPhoneNum();
            String userName = groupContactsDocNotFromDB.getUserName();
            if (!isContactPhoneNumberAndUsernameAlreadyPresent(contactPhoneNumber, userName)) {
                //we can go ahead to save now else,do nothing since it exists already
                try {
                    groupContactsDocRepository.save(groupContactsDocNotFromDB);
                    finalContactsList.add(groupContactsDocNotFromDB);
                } catch (Exception e) {
                    //do not add to List since it was not saved in db as error occured
                    log.info("{}", g.toJson(groupContactsDocNotFromDB) + " not saved ");
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

    /**
     * @param contactPhoneNum
     * @param userName
     * @return boolean
     * Check PhoneNumber And Username because A different user can save same number and it should still be valid
     */
    boolean isContactPhoneNumberAndUsernameAlreadyPresent(String contactPhoneNum, String userName) {
        boolean isContactPhoneNumberAlreadyPresent = false;
        GroupContactsDoc groupContactsDoc = groupContactsDocRepository.findByContactPhoneNumAndUserName(contactPhoneNum, userName);
        if (!(groupContactsDoc == null)) {
            isContactPhoneNumberAlreadyPresent = true;
        }
        return isContactPhoneNumberAlreadyPresent;
    }
}
