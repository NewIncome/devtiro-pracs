package com.devtiro.database01.dao.impl;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.devtiro.database01.domain.Book;

//Unit test
/* This integrates Mockito with JUnit 5
 * It enables Mockito to initialize mocks and inject them into the test class
 */
@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private BookDaoImpl underTest;

  @Test
  public void testThatCreateBookGeneratesCorrectSql() {   //descriptive method name
    //Step #1 in TDD. 2 in BookDao
    //.builder() method is available thanks to the @Builder annotation in the Book class
    Book book = Book.builder()      //helper that lets you build an object step-by-step 
          .isbn("978-1-12345-6789-0")
          .title("The Shadow in the Attic")
          .authorId(1L)
          .build();   //method to create the final object
    underTest.create(book);

    //Step #4 in TDD. & Run test to get Failure . 5 in BookDaoImpl
    /* this Mockito method checks whether a specific interaction occurred with the mocked jdbcTemplate */
    verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
            eq("978-1-12345-6789-0"),
            eq("The Shadow in the Attic"),
            eq(1L)
    );
  }

  @Test
  public void testThatFindOneBookGeneratesTheCorrectSql() {
    //T2 TDD1
    underTest.find("978-1-12345-6789-0");
    //T2 TDD4
    verify(jdbcTemplate).query(
            eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
            ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),   //T2 TDD6
            eq("978-1-12345-6789-0")
    );
  }

}
