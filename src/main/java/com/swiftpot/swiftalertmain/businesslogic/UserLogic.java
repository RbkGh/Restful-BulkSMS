package com.swiftpot.swiftalertmain.businesslogic;

import com.swiftpot.swiftalertmain.db.model.UserDoc;
import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;
import com.swiftpot.swiftalertmain.models.SuccessfulOutgoingPayload;
import com.swiftpot.swiftalertmain.models.UserDetailsResponse;
import com.swiftpot.swiftalertmain.repositories.UserDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         10-Oct-16 @ 1:29 PM
 */
@Service
public class UserLogic {

    @Autowired
    UserDocRepository userDocRepository;

    public OutgoingPayload getUserAccountDetails(String userId){
        OutgoingPayload outgoingPayload ;
        if(!isUserIdPresent(userId)){
            outgoingPayload = new ErrorOutgoingPayload("UserId does not exist");
        }else{
            UserDoc userDoc = userDocRepository.findOne(userId);
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse(userDoc.getId(),
                                                                              userDoc.getUserName(),
                                                                              userDoc.getFirstName(),
                                                                              userDoc.getLastName(),
                                                                              userDoc.getCreditBalance());
            outgoingPayload = new SuccessfulOutgoingPayload(userDetailsResponse);
        }
        return outgoingPayload;
    }

    public OutgoingPayload updatePasswordForUser(String userId,String currentPassword,String newPassword){
        OutgoingPayload outgoingPayload ;
        if(!isUserIdPresent(userId)){
            outgoingPayload = new ErrorOutgoingPayload("UserId does not exist");
        }else{
            if(isCurrentUserPasswordSameInDB(userId,currentPassword)) {
                UserDoc userDoc = userDocRepository.findOne(userId);
                userDoc.setPassword(newPassword);
                userDocRepository.save(userDoc);
                outgoingPayload = new SuccessfulOutgoingPayload("Password Changed Successfully");
            }else{
                outgoingPayload = new ErrorOutgoingPayload("Current Password is Incorrect,Hence Password was not changed!!");
            }
        }

        return outgoingPayload;
    }

    private boolean isCurrentUserPasswordSameInDB(String userId,String currentPassword){
        boolean isCurrentUserPasswordSameInDB = false;
        UserDoc userDoc = userDocRepository.findOne(userId);
        if(userDoc.getPassword().equals(currentPassword)){
            isCurrentUserPasswordSameInDB = true;
        }

        return isCurrentUserPasswordSameInDB;
    }
    private boolean isUserIdPresent(String userId){
        boolean isUserIdPresent = false;
        if(userDocRepository.exists(userId)){
            isUserIdPresent = true;
        }
        return isUserIdPresent;
    }
}
