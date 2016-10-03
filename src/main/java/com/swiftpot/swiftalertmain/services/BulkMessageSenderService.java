/**
 *
 */
package com.swiftpot.swiftalertmain.services;

import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
