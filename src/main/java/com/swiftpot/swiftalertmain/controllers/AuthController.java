package com.swiftpot.swiftalertmain.controllers;

import com.swiftpot.swiftalertmain.businesslogic.AuthLogic;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 8:45 PM
 */
@RestController
@RequestMapping(path = "")
public class AuthController {

    @Autowired
    AuthLogic authLogic;

    @RequestMapping(path = "/auth",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public OutgoingPayload signIn(@RequestBody SignInRequest signInRequest) throws ServletException{
        return authLogic.signIn(signInRequest);
    }

}
