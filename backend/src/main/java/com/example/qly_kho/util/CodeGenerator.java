package com.example.qly_kho.util;

import java.util.UUID;

public class CodeGenerator {

    private CodeGenerator() {
        // tránh new object
    }

    public static String generateUserCode() {
        return "USR-" + UUID.randomUUID().toString().substring(0, 8);
    }
}