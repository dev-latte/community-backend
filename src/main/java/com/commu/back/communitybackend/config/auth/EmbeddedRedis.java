package com.commu.back.communitybackend.config.auth;

import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class EmbeddedRedis {
    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        redisServer = new RedisServer();
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis(){
        redisServer.stop();
    }
}
