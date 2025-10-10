package com.devtiro.database;

import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;

public final class TestDataUtil {   //final, usual patter for utility classes

  private TestDataUtil(){     //not going to expose the default constructor, we don't want the class constructed at all""
  }

  public static Author createTestAuthorA() {
    return Author.builder()
            .name("Abigail Rose")
            .age(80)
            .build();
  }
  public static Author createTestAuthorB() {
    return Author.builder()
            .name("Thomas Cronin")
            .age(44)
            .build();
  }
  public static Author createTestAuthorC() {
    return Author.builder()
            .name("Jesse A Casey")
            .age(24)
            .build();
  }

  public static Book createTestBookA(final Author author) {
    return Book.builder()      //.builder() method is available thanks to the @Builder annotation in the Book class
          .isbn("978-1-2345-6789-0")
          .title("The Shadow in the Attic")
          .author(author)
          .build();   //helper that lets you build an object step-by-step
  }
  public static Book createTestBookB(final Author author) {
    return Book.builder()
          .isbn("978-1-2345-6789-1")
          .title("Beyond the Horizon")
          .author(author)
          .build();
  }
  public static Book createTestBookC(final Author author) {
    return Book.builder()
          .isbn("978-1-2345-6789-3")
          .title("The Last Ember")
          .author(author)
          .build();
  }

}
