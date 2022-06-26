package com.haruhi.security.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haruhi.security.entity.AccountInfo;
import com.haruhi.security.exception.AccountSessionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 该类提供在redis中操作session的方法。
 * @author 61711
 */
@Component
public class SessionOnRedisDAO {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void save(AccountInfo accountInfo) {
        try {
            String json = objectMapper.writeValueAsString(accountInfo);
            redisTemplate.opsForValue().set(accountInfo.getToken(), json, 6000, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public AccountInfo get(String token) throws AccountSessionNotFoundException {
        String json = redisTemplate.opsForValue().get(token);
        try {
            if (json != null) {
                redisTemplate.opsForValue().set(token, json, 6000, TimeUnit.SECONDS);
                return objectMapper.readValue(json, AccountInfo.class);
            } else {
                throw new AccountSessionNotFoundException("会话null");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String token) {
        redisTemplate.delete(token);
    }
}
