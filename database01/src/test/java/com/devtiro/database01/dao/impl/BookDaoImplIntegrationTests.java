package com.devtiro.database01.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.devtiro.database01.TestDataUtil;
import com.devtiro.database01.domain.Author;
import com.devtiro.database01.domain.Book;

@SpringBootTest
@Transactional
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
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);

    Book book = TestDataUtil.createTestBookA();
    book.setAuthorId(author.getId());
    underTest.create(book);

    Optional<Book> result = underTest.findOne(book.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {  //7. Create the test against the DB data
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);

    Book bookA = TestDataUtil.createTestBookA();
    bookA.setAuthorId(author.getId());
    underTest.create(bookA);
    Book bookB = TestDataUtil.createTestBookB();
    bookB.setAuthorId(author.getId());
    underTest.create(bookB);
    Book bookC = TestDataUtil.createTestBookC();
    bookC.setAuthorId(author.getId());
    underTest.create(bookC);

    List<Book> result = underTest.find();
    assertThat(result)
            .hasSize(3)
            .contains(bookA, bookB, bookC);
  }

  @Test
  public void testThatBookCanBeUpdated() {
    //create the author for the book
    Author author = TestDataUtil.createTestAuthorA();
    authorDao.create(author);

    //create the book template, then set the author_id, then create the book row in DB
    Book book = TestDataUtil.createTestBookA();
    book.setAuthorId(author.getId());
    underTest.create(book);

    //call the new 'update' feat
    book.setTitle("UPDATED");
    underTest.update(book.getIsbn(), book);

    //test/assert the feat
    Optional<Book> result = underTest.findOne(book.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

}
