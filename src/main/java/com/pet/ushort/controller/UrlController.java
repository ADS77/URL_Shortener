package com.pet.ushort.controller;
import com.pet.ushort.model.ShortenRequest;
import com.pet.ushort.service.UrlValidatorService;
import com.pet.ushort.service.manager.UrlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/urlShortener")
public class UrlController {
    @Autowired
    private Environment env;
    @Autowired
    private UrlManager urlManager;
    @Autowired
    private UrlValidatorService validatorService;
    private Logger logger = LoggerFactory.getLogger(UrlController.class);

    @PostMapping(value = "/shortenUrl", consumes = {"application/json"})
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody final ShortenRequest shortenRequest) {
        String longUrl = shortenRequest.getUrl();
        if (!validatorService.isValidUrl(longUrl)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Invalid URL"));
        }
        //String localUrl = "ad/ushort";
        //String shortenedUrl = urlManager.shortenUrl(localUrl, longUrl);
        String baseUrl = env.getProperty("shortener.base-url", "http://localhost:8080/ad/ushort");
        String shortenedUrl = urlManager.shortenUrl(baseUrl, longUrl);
        logger.info("Short URL generated: " + shortenedUrl);

        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", shortenedUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/redirect{key}")
    public ResponseEntity<Map<String, String>> getLongUrl(@PathVariable String key) {
        logger.info("Redirect hit...");
        if (key != null) {
            logger.info("key = ", key);
            String longUrl = urlManager.getUrlByID(key);
            if (longUrl != null) {
                Map<String, String> response = new HashMap<>();
                response.put("longUrl", longUrl);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "URL not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Invalid key"));
        }
    }
}




