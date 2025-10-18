package com.devtiro.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class MapperConfig {

  //To have access to the ModelMapper inside of our ApplicationContext
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
