package com.swiftpot.swiftalertmain.repositories;

import com.swiftpot.swiftalertmain.db.model.GroupsDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         02-Oct-16 @ 6:14 PM
 */
@Repository
public interface GroupsDocRepository extends MongoRepository<GroupsDoc,String> {
    GroupsDoc findByGroupId(String groupId);
}
