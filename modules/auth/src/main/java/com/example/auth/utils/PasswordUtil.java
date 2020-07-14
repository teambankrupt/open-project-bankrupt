package com.example.auth.utils;

import com.example.common.exceptions.nullpointer.NullPasswordException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    public static int PASS_MIN_SIZE = 6;
    public static final int CRYPTO_STRENGTH = 512;

    public enum EncType {SHA_ENCODER, BCRYPT_ENCODER}

    public static BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String encryptPassword(String password, EncType encryptType, String salt) throws NullPasswordException {
        if (password == null) throw new NullPasswordException("Password can not be empty!");
        if (encryptType.equals(EncType.BCRYPT_ENCODER))
            return getBCryptPasswordEncoder().encode(password);
        return getBCryptPasswordEncoder().encode(password);
    }

    public static boolean matches(String existingPassword, String password) throws NullPasswordException {
        if (existingPassword == null || password == null) throw new NullPasswordException("Password can not be empty!");
        return PasswordUtil.getBCryptPasswordEncoder().matches(password, existingPassword);
    }
}
