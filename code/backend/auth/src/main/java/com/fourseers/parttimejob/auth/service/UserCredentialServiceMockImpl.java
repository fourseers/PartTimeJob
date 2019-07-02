package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.auth.entity.UserCredential;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialServiceMockImpl implements UserCredentialService {
    @Override
    public UserCredential getById(long id) {
        UserCredential userCredential = new UserCredential();
        userCredential.setRid(id);
        userCredential.setUsername(Long.toHexString(new Long(id).hashCode()));
        userCredential.setPassword("asdfasdf");
        userCredential.setRole(UserCredential.Role.ROLE_USER);
        return userCredential;
    }

    @Override
    public UserCredential getByUsername(String username) {
        UserCredential userCredential = new UserCredential();
        userCredential.setRid(username.hashCode() % 100);
        userCredential.setUsername(username);
        userCredential.setPassword("asdfasdf");
        userCredential.setRole(UserCredential.Role.ROLE_USER);
        return userCredential;
    }
}
