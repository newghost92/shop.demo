package com.shop.demo.config.securityConfig.tokenStorage;

import com.shop.demo.enums.PersistableEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Primary
@Component
@ConditionalOnProperty(name = "app.token-cache.enabled", havingValue = "true")
public class RedisTokenCache {

    public enum Keys implements PersistableEnum<String> {
        TOKEN_SET("_tokens");

        private final String value;

        Keys(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }


    private JedisPool pool;
    private long ttl;

    public RedisTokenCache(TokenCacheConfiguration tokenCacheConfig) {
        this.pool = tokenCacheConfig.getPool();
        this.ttl = tokenCacheConfig.getTimeToLive();
    }

    public void storeToken(String username, String token) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(username, token);
            jedis.sadd(Keys.TOKEN_SET.value, token);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void refreshToken(String username, String token) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(username, token);
            jedis.sadd(Keys.TOKEN_SET.value, token);
        }
    }

    public void deleteToken(String token) {
        try (Jedis jedis = pool.getResource()) {
            // we'll simply leave the username entry to timeout
            jedis.srem(Keys.TOKEN_SET.value, token);
        }
    }

    public void deleteTokenForUser(String username) {
        try (Jedis jedis = pool.getResource()) {
            String token = jedis.get(username);
            if (token != null) {
                jedis.del(username);
                jedis.srem(Keys.TOKEN_SET.value, token);
            }
        }
    }

    public String getTokenByKey(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    public boolean isTokenStored(String token) {
        boolean tokenFound;

        try (Jedis jedis = pool.getResource()) {
            tokenFound = jedis.sismember(Keys.TOKEN_SET.value, token);
        }
        return tokenFound;
    }

    public void cacheByKey(Keys key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(key.value, value);
        }
    }

    public void deleteCacheByKey(Keys key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(key.value, value);
        }
    }

    public boolean isCacheStored(Keys key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.sismember(key.value, value);
        }
    }
}
