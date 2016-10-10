package com.swiftpot.swiftalertmain.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         10-Oct-16 @ 2:00 PM
 */
@Document(collection = "SuperUserDoc")
public class SuperUserDoc {

    @Id
    String id;

    String superName;

    String password;

}
