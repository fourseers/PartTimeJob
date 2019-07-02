package com.fourseers.parttimejob.auth.dao.impl;

import com.fourseers.parttimejob.auth.dao.UserCredentialDao;
import com.fourseers.parttimejob.auth.entity.UserCredential;
import com.fourseers.parttimejob.auth.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserCredentialDaoImpl implements UserCredentialDao {

    @Autowired
    UserCredentialRepository userCredentialRepository;

    public UserCredential findByUsername(String username) {
        return userCredentialRepository.findByUsername(username);
    }

    public UserCredential findByRid(long rid) {
        return userCredentialRepository.findByRid(rid);
    }

}
