package com.fourseers.parttimejob.arrangement.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages="com.fourseers.parttimejob")
@EnableJpaRepositories(basePackages="com.fourseers.parttimejob")
@EntityScan(basePackages="com.fourseers.parttimejob")
public class AppConfig {
}
