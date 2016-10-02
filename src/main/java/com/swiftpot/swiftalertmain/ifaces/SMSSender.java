package com.swiftpot.swiftalertmain.ifaces;


import com.swiftpot.swiftalertmain.models.SMSSenderRequest;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 2:38 PM
 */
public interface SMSSender {
    boolean isMessageSendingSuccessful(SMSSenderRequest smsSenderRequest);
}
