package com.swiftpot.swiftalertmain.repositories;

import com.swiftpot.swiftalertmain.db.model.SuperUserDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         10-Oct-16 @ 2:03 PM
 */
public interface SuperUserDocRepository extends MongoRepository<SuperUserDoc,String> {
    SuperUserDoc findBySuperNameAndPassword(String superName,String password);
}
