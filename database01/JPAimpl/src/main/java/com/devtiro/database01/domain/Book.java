package com.devtiro.database01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  private String isbn;

  private String title;

  //we could also use here an author object, but to keep it simple we'll use id
  private long authorId;

}
