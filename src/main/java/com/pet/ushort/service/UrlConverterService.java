package com.pet.ushort.service;

import org.springframework.stereotype.Service;

@Service
public class UrlConverterService {
    private static final String BASE65 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$";
    private static final int BASE = BASE65.length();

    public String encode(long id){
        StringBuilder sb = new StringBuilder();
        while (id > 0){
            sb.append(BASE65.charAt((int)id%BASE));
            id/=BASE;
        }
        return sb.reverse().toString();
    }

    public long decode(String key){
        long id = 0;
        for(char c : key.toCharArray()){
            id = id*BASE+BASE65.indexOf(c);
        }
        return id;
    }
}
