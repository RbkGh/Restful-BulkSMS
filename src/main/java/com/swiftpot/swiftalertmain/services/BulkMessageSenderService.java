/**
 *
 */
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
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Rodney on Jan 14, 2016 @ 12:06:19 AM<rbk.unlimited@gmail.com>
 * @url SwiftPot.com
 */
@Component
public class BulkMessageSenderService implements Runnable {

    @Autowired
    BaseMessageSender baseMessageSender;

    List<GroupContactsDoc> groupContactsDocsList;
    String message;
    String senderId;

    public BulkMessageSenderService() {

    }

    public BulkMessageSenderService(List<GroupContactsDoc> groupContactsDocsList, String message, String senderId) {
        // TODO Auto-generated constructor stub
        this.groupContactsDocsList = groupContactsDocsList;
        this.message = message;
        this.senderId = senderId;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        baseMessageSender.didEachMessageSendWithoutAnyError(groupContactsDocsList, message, senderId);

    }


}
