package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.db.model.UserDoc;
import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SuccessfulOutgoingPayload;
import com.swiftpot.swiftalertmain.repositories.SuperUserDocRepository;
import com.swiftpot.swiftalertmain.repositories.UserDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         10-Oct-16 @ 1:58 PM
 */
@RestController
@RequestMapping("/super")
public class SuperController {

    @Autowired
    SuperUserDocRepository superUserDocRepository;
    @Autowired
    UserDocRepository userDocRepository;

    @RequestMapping(path = "/updatebalance", method = RequestMethod.GET,consumes = MediaType.ALL_VALUE)
    public OutgoingPayload updateBalanceForUser(@PathParam("superName") String superName,
                                                @PathParam("password") String password,
                                                @PathParam("userName") String userName,
                                                @PathParam("balance") int balance) {
        OutgoingPayload outgoingPayload;
        if (!isSuperUserIdAndPassWordPresent(superName, password)) {
            outgoingPayload = new ErrorOutgoingPayload("SuperUser does not exist");
        } else {
            if (isUpdateOfUserBalanceSuccessful(userName, balance)) {
                outgoingPayload = new SuccessfulOutgoingPayload("Balance Updated Successfully!!");

            } else {
                outgoingPayload = new ErrorOutgoingPayload("userName probably does not exist");
            }
        }

        return outgoingPayload;
    }

    boolean isSuperUserIdAndPassWordPresent(String superName, String password) {
        boolean isSuperUserIdAndPassWordPresent = false;
        if (!(superUserDocRepository.findBySuperNameAndPassword(superName, password) == null)) {
            isSuperUserIdAndPassWordPresent = true;
        }
        return isSuperUserIdAndPassWordPresent;
    }

    boolean isUpdateOfUserBalanceSuccessful(String userName, int balance) {
        boolean isUpdateOfUserBalanceSuccessful = false;
        try {
            //get account
            UserDoc userDoc = userDocRepository.findByUserName(userName);
            int currentBalance = userDoc.getCreditBalance();
            int newBalance = currentBalance + balance;
            //set new balance
            userDoc.setCreditBalance(newBalance);
            userDocRepository.save(userDoc);
            isUpdateOfUserBalanceSuccessful = true;
        } catch (Exception e) {

        }

        return isUpdateOfUserBalanceSuccessful;
    }

}
