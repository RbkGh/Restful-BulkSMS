package com.swiftpot.swiftalertmain.businesslogic;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import com.swiftpot.swiftalertmain.db.model.UserDoc;
import com.swiftpot.swiftalertmain.models.*;
import com.swiftpot.swiftalertmain.repositories.GroupContactsDocRepository;
import com.swiftpot.swiftalertmain.repositories.UserDocRepository;
import com.swiftpot.swiftalertmain.services.BaseMessageSender;
import com.swiftpot.swiftalertmain.services.BulkMessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 2:53 PM
 */
public class MessagesLogic {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    Gson gson;
    @Autowired
    GroupContactsDocRepository groupContactsDocRepository;
    @Autowired
    UserDocRepository userDocRepository;
    @Autowired
    BaseMessageSender baseMessageSender;

    public OutgoingPayload sendSingleMessage(SingleMessagesRequest singleMessagesRequest) {
        log.info("SendSingleMessage Request : {}", gson.toJson(singleMessagesRequest));

        OutgoingPayload outgoingPayload ;
        String userName = singleMessagesRequest.getUserName();
        String contactPhoneNum = singleMessagesRequest.getRecieverNum();
        String message = singleMessagesRequest.getMessage();
        String senderId = singleMessagesRequest.getSenderId();

        if(!isCreditBalanceEnough(userName,1)){
            outgoingPayload = new ErrorOutgoingPayload("Insufficient Balance", null);
        }else{
            /**
             * deduct credit before transaction
             */
            deductCreditBeforeSendingMessageAndUpdateInDB(1,userName);
            /**
             * Initialize a List of GroupContactsDoc with a single element and pass to the BaseMessageSender service
             * to allow reporting of single messages too by setting empty strings to the firstName,lastName & groupId to
             * an empty string to show its a single message
             */
            List<GroupContactsDoc> groupContactsDocsList = new ArrayList<>(0);
            GroupContactsDoc groupContactsDoc = new GroupContactsDoc(userName,"","",contactPhoneNum,"");
            groupContactsDocsList.add(groupContactsDoc);

            if(baseMessageSender.didEachMessageSendWithoutAnyError(groupContactsDocsList, message, senderId)){
                outgoingPayload = new SuccessfulOutgoingPayload("Message Sent Successfully");
            }else{
                outgoingPayload = new ErrorOutgoingPayload("Message Not Sent");
            }



        }
        return outgoingPayload;
    }

    public OutgoingPayload sendMessagesInBulk(BulkMessagesRequest bulkMessagesRequest) {
        log.info("SendMessageInBulk Request : {}", gson.toJson(bulkMessagesRequest));

        OutgoingPayload outgoingPayload ;

        String groupId = bulkMessagesRequest.getGroupId();
        String userName = bulkMessagesRequest.getUserName();
        String senderId = bulkMessagesRequest.getSenderId();
        int numberofMessagesToSend = getNumberOfContactsForSpecificGroup(groupId);

        if (!(isCreditBalanceEnough(bulkMessagesRequest.getUserName(), numberofMessagesToSend))) {
            outgoingPayload = new ErrorOutgoingPayload("Insufficient Balance", null);
        } else {
            int noOfCreditsToDeduct = groupContactsDocRepository.findByGroupId(groupId).size();
            /**
             * deduct credit before transaction
             */
            deductCreditBeforeSendingMessageAndUpdateInDB(noOfCreditsToDeduct, userName);
            List<GroupContactsDoc> groupContactsDocsList = findContactsInGroupById(groupId);

            BulkMessageSenderService bulkSMSSenderLogic = new BulkMessageSenderService(groupContactsDocsList,
                    bulkMessagesRequest.getMessage(), senderId);

            new Thread(bulkSMSSenderLogic).start();

            outgoingPayload = new SuccessfulOutgoingPayload("Messages Sent Successfully");

        }

        return outgoingPayload;
    }

    List<GroupContactsDoc> findContactsInGroupById(String groupId) {
        return groupContactsDocRepository.findByGroupId(groupId);
    }

    boolean isCreditBalanceEnough(String userName, int numberOfMessagesToSend) {
        boolean isCreditBalanceEnough = false;
        int userCreditBalance = userDocRepository.findByUserName(userName).getCreditBalance();
        if (userCreditBalance >= numberOfMessagesToSend) {
            isCreditBalanceEnough = true;
        }

        return isCreditBalanceEnough;
    }

    int getNumberOfContactsForSpecificGroup(String groupId) {
        return groupContactsDocRepository.findByGroupId(groupId).size();
    }

    void deductCreditBeforeSendingMessageAndUpdateInDB(int noOfCreditsToDeduct, String userName) {
        UserDoc userDoc = userDocRepository.findByUserName(userName);
        int newCreditBalance = userDoc.getCreditBalance() - noOfCreditsToDeduct;
        userDoc.setCreditBalance(newCreditBalance);

        userDocRepository.save(userDoc);
    }


}
