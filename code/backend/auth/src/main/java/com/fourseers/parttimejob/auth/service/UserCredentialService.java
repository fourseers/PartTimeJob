package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.auth.entity.UserCredential;

public interface UserCredentialService {

    UserCredential getByUsername(String username);

    UserCredential getByRid(long rid);
}
