package com.pet.ushort.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlConverterService {
    private static Logger logger = LoggerFactory.getLogger(UrlConverterService.class);
    private static final String BASE65 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$";
    private static final long BASE = BASE65.length();

    public String encode(long id) {
        StringBuilder sb = new StringBuilder();
        if (id == 0) {
            return String.valueOf(BASE65.charAt(0));
        }
        while (id > 0) {
            sb.append(BASE65.charAt((int) (id % BASE)));
            id /= BASE;
        }
        return sb.reverse().toString();
    }

    public long decode(String key) {
        long id = 0;
        for (char c : key.toCharArray()) {
            int index = BASE65.indexOf(c);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character found in key: " + c);
            }
            id = id * BASE + index;
        }
        return id;
    }

}
