package com.fourseers.parttimejob.auth.repository;

import com.fourseers.parttimejob.auth.entity.UserCredential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserCredentialRepository extends MongoRepository<UserCredential, Integer> {

    @Query("{ 'username' : ?0 }")
    UserCredential findByUsername(String username);

    @Query("{ 'rid' : ?0 }")
    UserCredential findByRid(long rid);
}
