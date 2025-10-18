package com.devtiro.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor //needed for Jackson
@Builder
public class AuthorDto { //Author POJO

  private Long id;

  private String name;

  private Integer age;
  
}
