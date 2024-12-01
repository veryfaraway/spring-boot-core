package com.example.web.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	/**
	 * 메시지 소스 설정
	 * messages.properties 파일을 UTF-8로 읽어오도록 설정
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/messages"); // messages.properties 파일 위치
		messageSource.setDefaultEncoding("UTF-8"); // 인코딩 설정
		return messageSource;
	}

	/**
	 * 기본 로케일 설정
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.KOREAN); // 기본 로케일을 한국어로 설정
		return resolver;
	}
}
