package com.swiftpot.swiftalertmain.exceptions;

import com.swiftpot.swiftalertmain.models.ErrorOutgoingPayload;
import com.swiftpot.swiftalertmain.models.OutgoingPayload;

import javax.servlet.ServletException;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 10:47 PM
 */
public class InvalidCredentialsException extends ServletException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public OutgoingPayload returnInvalidCredentialsResponse(){
        OutgoingPayload outgoingPayload = new ErrorOutgoingPayload(this.getMessage(),null);
        return outgoingPayload;
    }
}
