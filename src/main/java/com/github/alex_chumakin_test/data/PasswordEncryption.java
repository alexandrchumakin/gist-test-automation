package com.github.alex_chumakin_test.data;

import java.util.Base64;

public class PasswordEncryption {

    static String decryptPassword(String encryptedPassword) {
        return new String(Base64.getDecoder().decode(encryptedPassword));
    }

}
