package com.swiftpot.swiftalertmain.businesslogic;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.MessagesDetailedReportDoc;
import com.swiftpot.swiftalertmain.db.model.MessagesReportDoc;
import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SuccessfulOutgoingPayload;
import com.swiftpot.swiftalertmain.repositories.MessagesDetailedReportDocRepository;
import com.swiftpot.swiftalertmain.repositories.MessagesReportDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 7:14 PM
 */
@Service
public class MessagesReportLogic {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    Gson gson;

    @Autowired
    MessagesReportDocRepository messagesReportDocRepository;
    @Autowired
    MessagesDetailedReportDocRepository messagesDetailedReportDocRepository;

    public OutgoingPayload getAllMessagesReportByUserName(String userName) {
        log.info("getMessagesReportRequest userName = " + userName);
        OutgoingPayload outgoingPayload;
        try {
            List<MessagesReportDoc> messagesReportDocsList = messagesReportDocRepository.findByUserName(userName);
            outgoingPayload = new SuccessfulOutgoingPayload(messagesReportDocsList);
        } catch (Exception e) {
            outgoingPayload = new ErrorOutgoingPayload("Group Id does not exist");
        }

        return outgoingPayload;
    }

    public OutgoingPayload getAllMessagesReportInDetailByMessageId(String messageId) {
        log.info("getMessagesReportRequest messageId = " + messageId);
        OutgoingPayload outgoingPayload;
        try {
            List<MessagesDetailedReportDoc> messagesDetailedReportDocList = messagesDetailedReportDocRepository.findByMessageId(messageId);
            outgoingPayload = new SuccessfulOutgoingPayload(messagesDetailedReportDocList);
        } catch (Exception e) {
            outgoingPayload = new ErrorOutgoingPayload("Group Id does not exist");
        }

        return outgoingPayload;
    }
}
