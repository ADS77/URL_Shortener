package com.pet.ushort.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {
    private static Logger logger = LoggerFactory.getLogger(UrlRepository.class);
    @Autowired
    private  RedisTemplate<String, String> redisTemplate;
    private final String idKey = "id";
    private final String urlKeyPrefix = "url:";


    public long incrementId(){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        Long id = valueOperations.increment(idKey);
        return id-1;
    }

    public void saveUrl(long id, String longUrl){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        logger.info("Saving as Key = "+ urlKeyPrefix+id);
        valueOperations.set(urlKeyPrefix+id, longUrl);
    }

    public String getLongUrl(long id){
        String key = urlKeyPrefix + id;
        try {
            ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
            String longUrl = valueOperations.get(key);
            if(longUrl == null){
                throw new RuntimeException("url for key: " + key + " not found !");
            }
            return longUrl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}