package com.fourseers.parttimejob.auth.security;

import com.fourseers.parttimejob.auth.service.MerchantUserService;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fourseers.parttimejob.auth.security.UserCredential.Role.ROLE_MERCHANT;
import static com.fourseers.parttimejob.auth.security.UserCredential.Role.ROLE_USER;

@Service("userDetailsService")
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Value("${app.wechat_password_placeholder}")
    private String WECHAT_PASSWD_PLACEHOLDER;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private MerchantUserService merchantUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl() {
        // log its creation
        LoggerFactory.getLogger(this.getClass()).debug("Loading custom user detail service.");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserCredential userCredential = new UserCredential();

        if(s.startsWith(WECHAT_USER_PREFIX)) {
            // wechat user
            String openid = s.substring(WECHAT_USER_PREFIX.length());
            WechatUser wechatUser = wechatUserService.findByOpenid(openid);
            if(wechatUser == null)
                throw new UsernameNotFoundException(String.format("Wechat user with openid[%s] not found.", openid));
            userCredential.setUsername(s);
            userCredential.setPassword(passwordEncoder.encode(WECHAT_PASSWD_PLACEHOLDER));
            userCredential.setRole(ROLE_USER);
            userCredential.setRid(wechatUser.getUserId());
            userCredential.setBanned(false);
        } else {
            // merchant user
            MerchantUser merchantUser = merchantUserService.findByUsername(s);
            if(merchantUser == null)
                throw new UsernameNotFoundException(String.format("Merchant user with username[%s] not found.", s));
            userCredential.setRid(merchantUser.getUserId());
            userCredential.setRole(ROLE_MERCHANT);
            userCredential.setUsername(merchantUser.getUsername());
            userCredential.setPassword(passwordEncoder.encode(merchantUser.getPassword()));
            userCredential.setBanned(merchantUser.getBanned());
        }

        return new SecurityUser(userCredential);
    }
}
