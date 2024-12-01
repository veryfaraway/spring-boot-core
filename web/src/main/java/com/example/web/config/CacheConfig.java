package com.example.web.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 캐시 관련 설정을 담당하는 설정 클래스
 */
@Configuration
@EnableCaching
public class CacheConfig {
	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("menuCache");
		cacheManager.setCaffeine(Caffeine.newBuilder()
			// 최대 크기 설정
			.maximumSize(100)
			// 마지막 접근 후 만료 시간 설정
			.expireAfterAccess(1, TimeUnit.HOURS)
			// 캐시 작성 후 만료 시간 설정
			.expireAfterWrite(1, TimeUnit.DAYS)
		);
		return cacheManager;
	}
}
