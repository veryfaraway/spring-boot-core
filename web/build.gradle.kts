plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":core"))
    
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.session:spring-session-jdbc")

    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("com.zaxxer:HikariCP") // HikariCP 명시적 추가

    runtimeOnly("com.h2database:h2:2.3.232")    // 로컬 환경용 H2 데이터베이스
    runtimeOnly("com.oracle.database.jdbc:ojdbc11:23.4.0.24.05")  // Oracle JDBC 드라이버
    runtimeOnly("com.oracle.database.jdbc:ucp:23.4.0.24.05")      // Oracle UCP

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}
