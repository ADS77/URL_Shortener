package com.pet.ushort.service.impl;

import com.pet.ushort.repository.UrlRepository;
import com.pet.ushort.service.UrlConverterService;
import com.pet.ushort.service.manager.UrlManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlManagerImpl implements UrlManager {
    private static final Logger logger = LoggerFactory.getLogger(UrlManagerImpl.class);
    @Autowired
    private  UrlRepository urlRepository;
    @Autowired
    private  UrlConverterService urlConverterService;


    @Override
    public String getUrlByID(String key) {
        if(key == null){
            return "key can not be empty !";
        }
        long id = urlConverterService.decode(key);
        logger.info("key = "+ key);
        logger.info("id = " + id);
        String url = urlRepository.getLongUrl(id);
        logger.info("longUrl : "+ url);
        return url;
    }

    @Override
    public String shortenUrl(String localUrl, String longUrl) {
        long id = urlRepository.incrementId();
        String key = urlConverterService.encode(id);
        urlRepository.saveUrl(id, longUrl);
        String baseString = formatUrl(localUrl);
        return (baseString+key);
    }

    private String formatUrl(String localUrl){
        String[] components = localUrl.split("/");
        StringBuilder sb = new StringBuilder();
        for (String component : components) {
            sb.append(component);
        }
        sb.append("/");
        return sb.toString();
    }
}