package com.shop.demo.config.securityConfig.tokenStorage;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

@Configuration
public class TokenCacheConfiguration {
    @Value("${app.token-cache.host}")
    String host;
    @Value("${app.token-cache.port}")
    int port;
    @Value("${app.token-cache.password}")
    String password;
    @Value("${app.token-cache.ttl}")
    long ttl;

    public JedisPool getPool(){
        if (!password.isEmpty()) {
            return new JedisPool(new GenericObjectPoolConfig<Jedis>(), this.host,
                this.port, Protocol.DEFAULT_TIMEOUT, password);
        }
        return new JedisPool(this.host, this.port);
    }

    public long getTimeToLive(){
        return ttl;
    }
}
