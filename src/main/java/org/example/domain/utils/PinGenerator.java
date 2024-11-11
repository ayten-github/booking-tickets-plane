package org.example.domain.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class PinGenerator {

    public static String generatePin(int length) {
        String pinUUID = UUID.randomUUID().toString().replace("-", "");
        return pinUUID.substring(0, length);
    }

}
