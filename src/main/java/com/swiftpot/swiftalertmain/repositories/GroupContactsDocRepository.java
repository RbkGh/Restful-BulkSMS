package com.swiftpot.swiftalertmain.repositories;

import com.swiftpot.swiftalertmain.db.model.GroupContactsDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         01-Oct-16 @ 10:14 PM
 */
@Repository
public interface GroupContactsDocRepository extends MongoRepository<GroupContactsDoc,String>{

    List<GroupContactsDoc> findByGroupId(String groupId);
    GroupContactsDoc findByContactPhoneNumAndUserName(String contactPhoneNum,String userName);
    GroupContactsDoc findByContactPhoneNumAndUserNameAndGroupId(String contactPhoneNum,String userName,String groupId);
}
