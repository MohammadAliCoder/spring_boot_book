package com.example.book_api.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("users","books", "patrons");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(600))
                .maximumSize(10_000));
        return cacheManager;
    }
}