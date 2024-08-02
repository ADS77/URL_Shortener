package com.pet.ushort.controller;

import com.pet.ushort.model.ShortenRequest;
import com.pet.ushort.service.UrlValidatorService;
import com.pet.ushort.service.manager.UrlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
@Controller
@RestController
@RequestMapping(value = "/urlShortener")
public class UrlController {
    @Autowired
    private  UrlManager urlManager;
    @Autowired
    private UrlValidatorService validatorService;
    private Logger logger = LoggerFactory.getLogger(UrlController.class);


    @PostMapping(value = "/shortenUrl", consumes = {"application/json"})
    public String shortenUrl(@RequestBody final ShortenRequest shortenRequest) throws Exception {
        String longUrl = shortenRequest.getUrl();

        String shortenedUrl = "";
        if( validatorService.isValidUrl(longUrl)){
            String localUrl = "ad/ushort";
            shortenedUrl = urlManager.shortenUrl(localUrl,longUrl);
            logger.info("short url : "+ shortenedUrl);
            return shortenedUrl;
        }
        else {
            throw new Exception("Invalid Url");
        }

    }

    @GetMapping(value = "/{key}")
    public RedirectView redirectUrl(@PathVariable String key){
        String redirectUrl = urlManager.getUrlByID(key);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }


}
