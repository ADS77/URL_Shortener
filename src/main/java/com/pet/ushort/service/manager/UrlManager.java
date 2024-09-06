package com.pet.ushort.service.manager;

import org.springframework.stereotype.Service;

@Service
public interface UrlManager {
    public String getUrlByID( String key) ;
    public String shortenUrl(String localUrl, String longUrl);
}