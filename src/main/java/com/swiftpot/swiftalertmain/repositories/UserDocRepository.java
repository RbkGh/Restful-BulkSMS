package com.swiftpot.swiftalertmain.repositories;

import com.swiftpot.swiftalertmain.db.model.UserDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         26-Sep-16 @ 9:09 PM
 */
@Repository
public interface UserDocRepository extends MongoRepository<UserDoc,String> {
    UserDoc findByUserNameAndPassword(String userName,String password);
}
