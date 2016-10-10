package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.businesslogic.UserLogic;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         10-Oct-16 @ 1:24 PM
 */
@RestController
@RequestMapping(path = "/api/v2")
public class UserController {

    @Autowired
    UserLogic userLogic;

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public OutgoingPayload getUserAccountDetails(@PathVariable String userId){
        return userLogic.getUserAccountDetails(userId);
    }

    @RequestMapping(path = "/user/passwordchange", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutgoingPayload updatePasswordForUser(@RequestBody UpdateUserRequest updateUserRequest){
        return userLogic.updatePasswordForUser(updateUserRequest.getUserId(),updateUserRequest.getCurrentPassword(), updateUserRequest.getNewPassword());
    }
}
