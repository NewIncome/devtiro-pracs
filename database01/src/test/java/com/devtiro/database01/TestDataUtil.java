package com.devtiro.database01;

import com.devtiro.database01.domain.Author;
import com.devtiro.database01.domain.Book;

public final class TestDataUtil {   //final, usual patter for utility classes

  private TestDataUtil(){     //not going to expose the default constructor, we don't want the class constructed at all""
  }

  public static Author createTestAuthorA() {
    return Author.builder()
            .id(1L)
            .name("Abigail Rose")
            .age(80)
            .build();
  }
  public static Author createTestAuthorB() {
    return Author.builder()
            .id(2L)
            .name("Thomas Cronin")
            .age(44)
            .build();
  }
  public static Author createTestAuthorC() {
    return Author.builder()
            .id(3L)
            .name("Jesse A Casey")
            .age(24)
            .build();
  }

  public static Book createTestBook() {
    return Book.builder()      //.builder() method is available thanks to the @Builder annotation in the Book class
          .isbn("978-1-12345-6789-0")
          .title("The Shadow in the Attic")
          .authorId(1L)
          .build();   //helper that lets you build an object step-by-step
  }

}
