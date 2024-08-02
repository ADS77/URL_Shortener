package com.pet.ushort.service.manager;

import com.pet.ushort.model.ShortenRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public interface UrlManager {
    public String getUrlByID( String key) ;
    public String shortenUrl(String localUrl, String longUrl);
}
