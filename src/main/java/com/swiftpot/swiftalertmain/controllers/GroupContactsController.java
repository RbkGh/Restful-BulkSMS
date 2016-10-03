package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.businesslogic.GroupContactsLogic;
import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import com.swiftpot.swiftalertmain.models.BulkGroupContactsCreationRequest;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         03-Oct-16 @ 2:11 AM
 */
@RestController
@RequestMapping(path = "/api/v2")
public class GroupContactsController {

    @Autowired
    GroupContactsLogic groupContactsLogic;

    @RequestMapping(path = "/contacts/contact",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public OutgoingPayload createOneGroupContact(@RequestBody GroupContactsDoc groupContactsDoc) {
      return groupContactsLogic.createOneGroupContact(groupContactsDoc);
    }

    @RequestMapping(path = "/contacts",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public OutgoingPayload createMultipleGroupContacts(@RequestBody BulkGroupContactsCreationRequest bulkGroupContactsCreationRequest) {
        return groupContactsLogic.createMultipleGroupContacts(bulkGroupContactsCreationRequest);
    }

    @RequestMapping(path = "/contacts/contact/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public OutgoingPayload deleteSingleGroupContactById(@PathVariable String id){
        return groupContactsLogic.deleteSingleGroupContactById(id);
    }

    @RequestMapping(path = "/contacts/contact", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutgoingPayload updateSingleGroupContact(@RequestBody GroupContactsDoc groupContactsDoc){
        return groupContactsLogic.updateSingleGroupContact(groupContactsDoc);
    }

}
