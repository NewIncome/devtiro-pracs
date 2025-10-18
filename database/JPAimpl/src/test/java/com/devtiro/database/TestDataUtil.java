package com.devtiro.database;

import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.domain.entities.BookEntity;

public final class TestDataUtil {   //final, usual patter for utility classes

  private TestDataUtil(){     //not going to expose the default constructor, we don't want the class constructed at all""
  }

  public static AuthorEntity createTestAuthorA() {
    return AuthorEntity.builder()
            //.id(1L)
            .name("Abigail Rose")
            .age(80)
            .build();
  }
  public static AuthorEntity createTestAuthorB() {
    return AuthorEntity.builder()
            //.id(2L)
            .name("Thomas Cronin")
            .age(44)
            .build();
  }
  public static AuthorEntity createTestAuthorC() {
    return AuthorEntity.builder()
            //.id(3L)
            .name("Jesse A Casey")
            .age(24)
            .build();
  }

  public static BookEntity createTestBookA(final AuthorEntity author) {
    return BookEntity.builder()      //.builder() method is available thanks to the @Builder annotation in the Book class
          .isbn("978-1-2345-6789-0")
          .title("The Shadow in the Attic")
          .author(author)
          .build();   //helper that lets you build an object step-by-step
  }
  public static BookEntity createTestBookB(final AuthorEntity author) {
    return BookEntity.builder()
          .isbn("978-1-2345-6789-1")
          .title("Beyond the Horizon")
          .author(author)
          .build();
  }
  public static BookEntity createTestBookC(final AuthorEntity author) {
    return BookEntity.builder()
          .isbn("978-1-2345-6789-3")
          .title("The Last Ember")
          .author(author)
          .build();
  }

}
