package com.devtiro.database01;

import com.devtiro.database01.domain.Author;

public final class TestDataUtil {   //final, usual patter for utility classes

  private TestDataUtil(){     //not going to expose the default constructor, we don't want the class constructed at all""
  }

  public static Author createTestAuthor() {
    Author author = Author.builder()
            .id(1L)
            .name("Abigail Rose")
            .age(80)
            .build();
    return author;
  }

}
