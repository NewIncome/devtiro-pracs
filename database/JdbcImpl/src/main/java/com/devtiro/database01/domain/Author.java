package com.devtiro.database01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder        //...explain...
public class Author {
  
  //with long it would default to '0', with object it defaults to 'null'
  private Long id;

  private String name;

  private Integer age;

}
