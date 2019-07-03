package com.fourseers.parttimejob.auth.service.impl;

import com.fourseers.parttimejob.auth.dao.UserCredentialDao;
import com.fourseers.parttimejob.auth.entity.UserCredential;
import com.fourseers.parttimejob.auth.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {

    @Autowired
    UserCredentialDao userCredentialDao;

    @Override
    public UserCredential getById(long id) {
        return userCredentialDao.findByRid(id);
    }

    public UserCredential getByUsername(String username) {
        return userCredentialDao.findByUsername(username);
    }

    public UserCredential getByRid(long rid) {
        return userCredentialDao.findByRid(rid);
    }
}
