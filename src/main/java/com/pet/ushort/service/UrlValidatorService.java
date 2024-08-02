package com.pet.ushort.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class UrlValidatorService {
    private static final String URL_REGEX = "^(https?|ftp)://[^\s/$.?#].[^\s]*$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public boolean isValidUrl(String url){
        if (url == null || url.isEmpty()){
            return false;
        }
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.matches()){
            return false;
        }
        return true;
    }
}
