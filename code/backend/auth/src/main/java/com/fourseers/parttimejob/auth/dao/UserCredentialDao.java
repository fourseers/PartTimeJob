package com.fourseers.parttimejob.auth.dao;

import com.fourseers.parttimejob.auth.entity.UserCredential;

public interface UserCredentialDao {

    UserCredential findByUsername(String username);

    UserCredential findByRid(long rid);
}
