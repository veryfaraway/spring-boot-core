package com.example.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA 관련 설정을 담당하는 설정 클래스입니다.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
