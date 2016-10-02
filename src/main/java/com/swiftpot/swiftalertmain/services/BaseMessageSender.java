package com.swiftpot.swiftalertmain.services;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import com.swiftpot.swiftalertmain.db.model.MessagesDetailedReportDoc;
import com.swiftpot.swiftalertmain.db.model.MessagesReportDoc;
import com.swiftpot.swiftalertmain.db.model.UserDoc;
import com.swiftpot.swiftalertmain.helpers.DeliveryStatus;
import com.swiftpot.swiftalertmain.ifaces.SMSSender;
import com.swiftpot.swiftalertmain.models.SMSSenderRequest;
import com.swiftpot.swiftalertmain.repositories.GroupsDocRepository;
import com.swiftpot.swiftalertmain.repositories.MessagesDetailedReportDocRepository;
import com.swiftpot.swiftalertmain.repositories.MessagesReportDocRepository;
import com.swiftpot.swiftalertmain.repositories.UserDocRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 10:45 PM
 */
public class BaseMessageSender {

    @Autowired
    SMSSender smsSender;
    @Autowired
    MessagesReportDocRepository messagesReportDocRepository;
    @Autowired
    MessagesDetailedReportDocRepository messagesDetailedReportDocRepository;
    @Autowired
    UserDocRepository userDocRepository;
    @Autowired
    GroupsDocRepository groupsDocRepository;


    private final Logger log = LoggerFactory.getLogger(getClass());

    public boolean didEachMessageSendWithoutAnyError(List<GroupContactsDoc> groupContactsDocsList, String message, String senderId) {
        boolean wereAllMessagesSent =false;
        Gson gson = new Gson();
        // Set message which will be used for all contacts in group
        String messageToSendGlobal = message;
        // Set SenderId to be used for all contacts in group
        String senderIdGlobal = senderId;
        // Set MessageId to be used for all contacts in group wether it was sent or not to help in reporting to clients
        String messageIdGlobal = UUID.randomUUID().toString().toUpperCase().substring(0, 40);
        /**
         *Number of unsuccessful messages,this will be used to add back to customer's credit since it
         * has been deducted already before list is processed here
         */
        ArrayList<Boolean> unsuccessfulMessagesCount = new ArrayList<>(0);
        /**
         * find creditBalance before transaction
         */
        Date dateBulkMessageRequestCame = Date.from(Instant.now());
        int creditBefore = userDocRepository.findByUserName(groupContactsDocsList.get(0).getUserName()).getCreditBalance();

        for (GroupContactsDoc singleContact : groupContactsDocsList) {
            log.info("\n\n==========CONTACT (" + groupContactsDocsList.indexOf(singleContact)
                    + ") DETAILS AND MESSAGE==========\n:\t");
            log.info("{}**message={}", gson.toJson(singleContact), messageToSendGlobal);
            log.info("\n\n==========CONTACT (" + groupContactsDocsList.indexOf(singleContact)
                    + ") END===========================\n\n");
            /**
             * remember only the PhoneNumber of the contact is special,
             * message and senderId are same for all contacts in the group
             */
            String contactRecipientPhoneNum = singleContact.getContactPhoneNum();
            SMSSenderRequest smsSenderRequest = new SMSSenderRequest(senderIdGlobal,messageToSendGlobal,contactRecipientPhoneNum);
            String groupId = singleContact.getGroupId();

            MessagesDetailedReportDoc messagesDetailedReportDoc= new MessagesDetailedReportDoc(Date.from(Instant.now()),
                    messageIdGlobal,
                    contactRecipientPhoneNum,
                    senderIdGlobal,
                    groupId);

            if(!smsSender.isMessageSendingSuccessful(smsSenderRequest)){
                unsuccessfulMessagesCount.add(false);
                /**
                 * Set Delivery Status to NO,since message was not able to be sent
                 */
                messagesDetailedReportDoc.setDeliveryStatus(DeliveryStatus.NO.toString());
                messagesDetailedReportDocRepository.save(messagesDetailedReportDoc);
            }else{
                /**
                 * Set Delivery Status to YES,since message was sent successfully
                 */
                messagesDetailedReportDoc.setDeliveryStatus(DeliveryStatus.YES.toString());
                messagesDetailedReportDocRepository.save(messagesDetailedReportDoc);
            }

        }

        if(!unsuccessfulMessagesCount.isEmpty()){
            int numberOfCreditBalanceToReturnToCustomer = unsuccessfulMessagesCount.size();
            /**
             * get first contact's userName,all the same,but choose first one since list may,could and will contain only one
             * when its a single message
             */
            String userName = groupContactsDocsList.get(0).getUserName();
            returnCreditBalanceToCustomer(numberOfCreditBalanceToReturnToCustomer, userName);


            wereAllMessagesSent = false;
        }else{
            wereAllMessagesSent = true;
        }

        /**
         *
         * Finally save A MessageReportDoc with number of messages,credit before and after and others after saving
         * each message sent into the MessageReportDocDetailed Document
         *
         */
        int creditAfter = userDocRepository.findByUserName(groupContactsDocsList.get(0).getUserName()).getCreditBalance();
        MessagesReportDoc messagesReportDoc = new MessagesReportDoc();
        String groupIdTemporary = groupContactsDocsList.get(0).getGroupId();
        /**
         * set groupName to Empty if finding by GroupId fails,since we are passing empty groupId for a single message,groupId can be empty in some cases
         */
        String groupName = StringUtils.defaultIfEmpty(groupsDocRepository.findByGroupId(groupIdTemporary).getGroupName(), "");
        int totalNumOfMessagesTried = groupContactsDocsList.size();

        messagesReportDoc.setDateCreated(dateBulkMessageRequestCame);
        messagesReportDoc.setMessageId(messageIdGlobal);
        messagesReportDoc.setGroupName(groupName);
        messagesReportDoc.setGroupId(groupIdTemporary);
        messagesReportDoc.setNoOfMessages(String.valueOf(totalNumOfMessagesTried));
        messagesReportDoc.setCreditBefore(creditBefore);
        messagesReportDoc.setCreditAfter(creditAfter);

        messagesReportDocRepository.save(messagesReportDoc);

        return wereAllMessagesSent;
    }

    void returnCreditBalanceToCustomer(int noOfCreditsToDeduct, String userName){

        UserDoc userDoc = userDocRepository.findByUserName(userName);

        int newCreditBalance = userDoc.getCreditBalance() + noOfCreditsToDeduct;
        userDoc.setCreditBalance(newCreditBalance);

        userDocRepository.save(userDoc);
    }
}
