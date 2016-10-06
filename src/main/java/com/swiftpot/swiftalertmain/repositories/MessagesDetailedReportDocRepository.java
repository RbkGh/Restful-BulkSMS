package com.swiftpot.swiftalertmain.repositories;

import com.swiftpot.swiftalertmain.db.model.MessagesDetailedReportDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 4:57 PM
 */
@Repository
public interface MessagesDetailedReportDocRepository extends MongoRepository<MessagesDetailedReportDoc,String> {
    List<MessagesDetailedReportDoc> findByMessageId(String messageId);
}
