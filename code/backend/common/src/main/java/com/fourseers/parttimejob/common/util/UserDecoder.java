package com.fourseers.parttimejob.common.util;

import org.springframework.stereotype.Component;

@Component
public class UserDecoder {

    public static boolean isWechatUser(String token, String prefix) {
        return token.startsWith(prefix);
    }

    public static boolean isMerchantUser(String token, String prefix) {
        return !isWechatUser(token, prefix);
    }

    public static String getWechatUserOpenid(String token, String prefix) {
        return token.substring(prefix.length());
    }

    public static String getMerchantUsername(String token) {
        return token;
    }
}
