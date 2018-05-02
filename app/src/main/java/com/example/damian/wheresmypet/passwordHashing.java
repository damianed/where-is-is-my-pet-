package com.example.damian.wheresmypet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordHashing {

    public static String hashPassword(String salt, String password) {
        String saltedPassword = salt + password;
        return generateHash(saltedPassword);
    }

    private static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (byte b : hashedBytes) {
                hash.append(digits[(b & 0xf0) >> 4]);
                    hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException ignored) {}

        return hash.toString();
    }
}
