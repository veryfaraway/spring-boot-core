plugins {
    java
    `java-library`
    id("org.springframework.boot") // Spring Boot 플러그인 추가
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.3.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("org.hibernate.validator:hibernate-validator")
}

tasks.bootJar {
    enabled = false // core 모듈은 실행 가능한 jar가 필요없음
}

tasks.jar {
    enabled = true // 일반 jar 생성 활성화
}
