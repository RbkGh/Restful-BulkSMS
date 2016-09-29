package com.swiftpot.swiftalertmain.businesslogic;

import com.google.gson.Gson;
import com.swiftpot.swiftalertmain.db.model.UserDoc;
import com.swiftpot.swiftalertmain.exceptions.InvalidCredentialsException;
import com.swiftpot.swiftalertmain.models.*;
import com.swiftpot.swiftalertmain.repositories.UserDocRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Date;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 9:03 PM
 */
@Service
public class AuthLogic {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    UserDocRepository userDocRepository;
    @Autowired
    Gson gson;

    public OutgoingPayload signIn(SignInRequest signInRequest) throws ServletException{
        log.info("Incoming signinRequest = {}",gson.toJson(signInRequest));
        OutgoingPayload outgoingPayload ;
        if(isUserCredentialsOk(signInRequest)){
            UserDoc userDoc = userDocRepository.findByUserNameAndPassword(signInRequest.getUserName(), signInRequest.getPassword());

            String jwtToken = Jwts.builder().setSubject(signInRequest.getUserName())
                /*.claim("roles", userDb.get(login.name))*/.setIssuedAt(new Date()).claim("myName", "Rodney")
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

            SignInResponse signInResponseObject = new SignInResponse(userDoc.getId(),userDoc.getUserName(),userDoc.getCreditBalance());
            signInResponseObject.setFirstName(userDoc.getFirstName());
            signInResponseObject.setLastName(userDoc.getLastName());
            signInResponseObject.setToken(jwtToken);
            outgoingPayload = new SuccessfulOutgoingPayload(signInResponseObject);
        }
        else{
            //throw new ServletException("Invalid login credentials");
           //outgoingPayload = new ErrorOutgoingPayload("Wrong Username or PassWord",null);
            InvalidCredentialsException invalidCredentialsException = new InvalidCredentialsException("Invalid Credentials");
            outgoingPayload=invalidCredentialsException.returnInvalidCredentialsResponse();
        }

        return outgoingPayload;
    }

    private boolean isUserCredentialsOk(SignInRequest signInRequest){
        boolean isUserCredentialsOk = false;
        UserDoc userDoc = userDocRepository.findByUserNameAndPassword(signInRequest.getUserName(), signInRequest.getPassword());
        if(!(userDoc == null)){
            isUserCredentialsOk = true;
        }else{
            //return false;
        }
        return isUserCredentialsOk;
    }
}
