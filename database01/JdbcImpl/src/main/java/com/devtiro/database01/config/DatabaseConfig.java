package com.devtiro.database01.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration  //Declares that a class is a source of Bean definitions
public class DatabaseConfig {
  
  /*
   * @Bean , Marks a method whose return value should be registered as a Bean in the Spring application context
   */
  @Bean
  public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
      return new JdbcTemplate(dataSource);
  }
  
}
