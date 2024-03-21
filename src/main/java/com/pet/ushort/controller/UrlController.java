package com.pet.ushort.controller;

import com.pet.ushort.model.Url;
import com.pet.ushort.service.manager.UrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/urlShortener")
public class UrlController {
    @Autowired
    private UrlManager urlManager;

    @PostMapping()
    @ResponseBody
    public ResponseEntity shortenUrl(@RequestHeader String url){
        Url shortUrl = urlManager.shortenUrl(url);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity getUrl(@RequestHeader String key){
        String url = urlManager.getUrlByKey(key);
        return ResponseEntity.ok(url);
    }


}
