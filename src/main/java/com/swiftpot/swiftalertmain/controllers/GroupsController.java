package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.businesslogic.GroupsLogic;
import com.swiftpot.swiftalertmain.db.model.GroupsDoc;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 11:31 PM
 */
@RestController
@RequestMapping(path = "/api/v2")
public class GroupsController {

    @Autowired
    GroupsLogic groupsLogic;


    @RequestMapping(path = "/groups/{userName}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public OutgoingPayload getAllGroups(@PathVariable String userName) {
        return groupsLogic.getAllGroups(userName);
    }

    @RequestMapping(path = "/groups/group", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutgoingPayload updateOneGroup(@RequestBody GroupsDoc groupsDoc) {
        return groupsLogic.updateOneGroup(groupsDoc);
    }
    @RequestMapping(path = "/groups/group", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutgoingPayload deleteOneGroup(@RequestBody GroupsDoc groupsDoc) {
        return groupsLogic.deleteOneGroup(groupsDoc);
    }

    @RequestMapping(path = "/groups/group", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutgoingPayload createOneGroup(@RequestBody GroupsDoc groupsDoc) {
        return groupsLogic.createOneGroup(groupsDoc);
    }
}

