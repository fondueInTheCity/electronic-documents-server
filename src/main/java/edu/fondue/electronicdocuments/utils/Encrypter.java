package edu.fondue.electronicdocuments.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Encrypter {

    public String encrypt(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(UTF_8));
    }
}
