package com.fourseers.parttimejob.auth.security;

import com.fourseers.parttimejob.auth.entity.UserCredential;
import com.fourseers.parttimejob.auth.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

//@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserCredential userCredential = userCredentialService.getByUsername(username);
        Logger.getLogger(this.getClass().getName()).info("Loading user, username: " + username);
        if (userCredential == null)
            throw new UsernameNotFoundException("User " + username + " not found.");

        return new SecurityUser(userCredential);
    }
}
