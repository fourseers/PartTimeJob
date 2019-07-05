package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.auth.entity.UserCredential;

public interface UserCredentialService {
    UserCredential getById(long id);
    UserCredential getByUsername(String username);
}
