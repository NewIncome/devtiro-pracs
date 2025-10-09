package com.devtiro.database01.dao.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.devtiro.database01.TestDataUtil;
import com.devtiro.database01.domain.Book;

//Unit test
/* This integrates Mockito with JUnit 5
 * It enables Mockito to initialize mocks and inject them into the test class
 */
@ExtendWith(MockitoExtension.class)
@Transactional
public class BookDaoImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private BookDaoImpl underTest;

  @Test
  public void testThatCreateBookGeneratesCorrectSql() {   //descriptive method name
    //Step #1 in TDD. 2 in BookDao
    Book book = TestDataUtil.createTestBookA();   //method to create the final object
    underTest.create(book);

    //Step #4 in TDD. & Run test to get Failure . 5 in BookDaoImpl
    /* this Mockito method checks whether a specific interaction occurred with the mocked jdbcTemplate */
    verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
            eq("978-1-2345-6789-0"),
            eq("The Shadow in the Attic"),
            eq(1L)
    );
  }

  @Test
  public void testThatFindOneBookGeneratesTheCorrectSql() {
    //T2 TDD1
    underTest.findOne("978-1-2345-6789-0");
    //T2 TDD4
    verify(jdbcTemplate).query(
            eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
            ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),   //T2 TDD6
            eq("978-1-2345-6789-0")
    );
  }

  @Test
  public void testThatBookCanFindGeneratesCorrectSql() {  //1. create the test method
    underTest.find(); //2. call to test the new to impl method. Then go to Author interface to define
    //5. pre-test the implementation. Test to fail
    verify(jdbcTemplate).query(
              eq("SELECT isbn, title, author_id FROM books"),
              ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
    );
  }

  @Test
  public void testThatUpdateBookGeneratesCorrectSql() {
    Book book = TestDataUtil.createTestBookA();

    underTest.update(book.getIsbn(), book);

    verify(jdbcTemplate).update(
              "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
              "978-1-2345-6789-0", "The Shadow in the Attic", 1L, "978-1-2345-6789-0"
    );
  }

}
