package com.devtiro.database.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBannerConfig {
  @Bean
  public SpringApplication customSpringApplication() {
    SpringApplication app = new SpringApplication();
    app.setBanner(new CustomContextBanner());
    System.out.println("Applying CustomContextBanner for tests");
    return app;
  }
}