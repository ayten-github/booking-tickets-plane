package org.example.domain.utils;

import java.security.SecureRandom;

public class PinGenerator {
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generatePin(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder pin = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            pin.append(CHAR_POOL.charAt(index));
        }

        return pin.toString();
    }
}