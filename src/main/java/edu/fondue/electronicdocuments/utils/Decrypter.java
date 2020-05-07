package edu.fondue.electronicdocuments.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Decrypter {

    public String decrypt(String value) {
        return new String(Base64.getDecoder().decode(value.getBytes(UTF_8)), UTF_8);
    }
}
