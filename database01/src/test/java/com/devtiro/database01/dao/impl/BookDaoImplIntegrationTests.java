package com.devtiro.database01.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devtiro.database01.TestDataUtil;
import com.devtiro.database01.domain.Author;
import com.devtiro.database01.domain.Book;

@SpringBootTest
public class BookDaoImplIntegrationTests {

  private AuthorDaoImpl authorDao;

  private BookDaoImpl underTest;

  @Autowired
  public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDaoImpl authorDao) {
    this.underTest = underTest;
    this.authorDao = authorDao;
  }

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    Author author = TestDataUtil.createTestAuthor();
    authorDao.create(author);

    Book book = TestDataUtil.createTestBook();
    book.setAuthorId(author.getId());
    underTest.create(book);

    Optional<Book> result = underTest.find(book.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

}
