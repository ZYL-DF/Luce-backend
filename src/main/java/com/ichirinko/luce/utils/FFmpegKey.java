package com.ichirinko.luce.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class FFmpegKey {

    private static final String ALPHA_NUMERIC_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString() {


        byte[] bytes = new byte[16];
        random.nextBytes(bytes);

        StringBuilder sb = new StringBuilder(16);
        for (byte b : bytes) {
            // 取绝对值以避免负数，然后对字符集大小取模
            int index = Math.abs(b) % ALPHA_NUMERIC_CHARACTERS.length();
            char character = ALPHA_NUMERIC_CHARACTERS.charAt(index);
            sb.append(character);
        }
        return sb.toString();
    }
}
