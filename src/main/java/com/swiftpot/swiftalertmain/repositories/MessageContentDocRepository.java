package com.swiftpot.swiftalertmain.repositories;

import com.swiftpot.swiftalertmain.db.model.MessageContentsDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         13-Oct-16 @ 7:56 AM
 */
@Repository
public interface MessageContentDocRepository extends MongoRepository<MessageContentsDoc,String>{
    MessageContentsDoc findByMessageId(String messageId);
}
