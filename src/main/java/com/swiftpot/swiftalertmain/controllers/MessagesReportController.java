package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.businesslogic.MessagesReportLogic;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 7:13 PM
 */
@RequestMapping("/api/v2/messages")
public class MessagesReportController {

    @Autowired
    MessagesReportLogic messagesReportLogic;

    @RequestMapping(path = "/report/{groupId}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OutgoingPayload getMessagesReport(@PathVariable String groupId){
        return messagesReportLogic.getMessagesReport(groupId);
    }
}
