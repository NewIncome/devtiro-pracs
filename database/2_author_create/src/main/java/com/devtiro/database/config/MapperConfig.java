package com.devtiro.database.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {

      ModelMapper modelMapper = new ModelMapper();
      /* Incantation to:
       * "Match" Nested  Objects and allow us to convert from one to the other.
       * Thus,
       * When we send a nested-object within the DTO it will then be converted
       * to a nested-Entity-Object,
       * and that! is all JPA needs in order to "Cascade" and do those saves and
       * do those updates
       * To really harness the power of NestedObjects in Spring-Data-JPA */
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

      return modelMapper;

    }

}
