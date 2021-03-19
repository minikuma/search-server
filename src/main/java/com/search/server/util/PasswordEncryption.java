package com.search.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 패스워드 암호화 Util
 * @version 1.0
 * @author jeonjihoon
 */

public class PasswordEncryption {

    public static String encode(String password) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            md5 = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            md5 = null;
        }
        return md5;
    }
}
