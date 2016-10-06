package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.businesslogic.MessagesReportLogic;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 7:13 PM
 */
@RestController
@RequestMapping("/api/v2/messages")
public class MessagesReportController {

    @Autowired
    MessagesReportLogic messagesReportLogic;

    @RequestMapping(path = "/report/{userName}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OutgoingPayload getAllMessagesReportByUserName(@PathVariable String userName){
        return messagesReportLogic.getAllMessagesReportByUserName(userName);
    }

    @RequestMapping(path = "/report/detailed/{messageId}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OutgoingPayload getAllMessagesReportInDetailByMessageId(@PathVariable String messageId){
        return messagesReportLogic.getAllMessagesReportInDetailByMessageId(messageId);
    }
}
