package com.swiftpot.swiftalertmain.businesslogic;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.GroupsDoc;
import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SuccessfulOutgoingPayload;
import com.swiftpot.swiftalertmain.repositories.GroupsDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 11:33 PM
 */
@Service
public class GroupsLogic {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    Gson g;

    @Autowired
    GroupsDocRepository groupsDocRepository;

    public OutgoingPayload getAllGroups(String userName) {
        log.info("GetAllGroups Registered To Username Request :" + userName);
        OutgoingPayload outgoingPayload;
        try {
            List<GroupsDoc> groupsDocList = groupsDocRepository.findAllByUserName(userName);
            outgoingPayload = new SuccessfulOutgoingPayload(groupsDocList);
        } catch (Exception e) {
            outgoingPayload = new ErrorOutgoingPayload("Could Not Get Groups.Are you sure user exists?");
        }

        return outgoingPayload;
    }

    public OutgoingPayload updateOneGroup(GroupsDoc groupsDoc) {
        log.info("Update One GroupName Request : {}", groupsDoc);
        OutgoingPayload outgoingPayload;

        try {
            boolean isDocumentPresent = groupsDocRepository.exists(groupsDoc.getId());
            if (isDocumentPresent) {
                GroupsDoc groupsDocFinal = groupsDocRepository.save(groupsDoc);
                outgoingPayload = new SuccessfulOutgoingPayload(groupsDocFinal);
            } else {
                outgoingPayload = new ErrorOutgoingPayload("Entity does not exist,bro!!:)");
            }
        } catch (Exception e) {
            log.info("Exception cause : " + e.getCause().getMessage());
            outgoingPayload = new ErrorOutgoingPayload("Could Not Update entity with id : {}.I don't think id exists,bruh :)", groupsDoc.getId());
        }
        return outgoingPayload;
    }

    public OutgoingPayload deleteOneGroup(GroupsDoc groupsDoc) {
        log.info("Delete One GroupName Request : {}", groupsDoc);
        OutgoingPayload outgoingPayload;

        try {
            boolean isDocumentPresent = groupsDocRepository.exists(groupsDoc.getId());
            if (isDocumentPresent) {
                groupsDocRepository.delete(groupsDoc);
                outgoingPayload = new SuccessfulOutgoingPayload("Deleted Successfully");
            } else {
                outgoingPayload = new ErrorOutgoingPayload("Entity does not exist,bro!!:)");
            }
        } catch (Exception e) {
            log.info("Exception cause : " + e.getCause().getMessage());
            outgoingPayload = new ErrorOutgoingPayload("Bro,I couldn't delete a Group with Id of {}.Are you drunk or what?:P =D", groupsDoc.getId());
        }
        return outgoingPayload;

    }

    public OutgoingPayload createOneGroup(GroupsDoc groupsDoc) {
        log.info("Create One GroupName Request : {}", groupsDoc);
        /**
         * generate 40char groupId and save to db,this is used in both MessageReport and MessageReportDetailed for querying
         */
        String groupId = UUID.randomUUID().toString().toUpperCase().substring(0, 40);
        groupsDoc.setGroupId(groupId);

        OutgoingPayload outgoingPayload;

        try {
            GroupsDoc groupsDocFinal = groupsDocRepository.save(groupsDoc);
            outgoingPayload = new SuccessfulOutgoingPayload("Created Successfully",groupsDocFinal);

        } catch (Exception e) {
            log.info("Exception cause : " + e.getCause().getMessage());
            outgoingPayload = new ErrorOutgoingPayload("Bro,I couldn't save a Group with Id of {}.Boy,I'm stoned!!:P =D", groupsDoc.getId());
        }
        return outgoingPayload;
    }
}