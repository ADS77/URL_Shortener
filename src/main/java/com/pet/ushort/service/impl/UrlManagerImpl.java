package com.pet.ushort.service.impl;

import com.pet.ushort.model.Url;
import com.pet.ushort.service.manager.UrlManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Slf4j
public class UrlManagerImpl implements UrlManager {
    @Autowired
    private RedisTemplate<String,Url> redisTemplate;

    @Override
    public String getUrlByKey(String key) {
        Url url = redisTemplate.opsForValue().get(key);
        return url.getUrl();
    }

    @Override
    public Url shortenUrl(String url) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(url.getBytes(StandardCharsets.UTF_8));
            String key = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
            return Url.builder().key(key).createdAt(LocalDateTime.now()).url(url).build();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not found" + e.getMessage());
        }
    }
}
