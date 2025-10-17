package com.devtiro.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.hibernate.engine.jdbc.env.internal.LobCreationLogging_.logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
public class BookRepositoryIntegrationTests {

  private BookRepository underTest;

  private AuthorRepository authorRepository;

  @Autowired
  public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
    this.underTest = underTest;
    this.authorRepository = authorRepository;
  }

  /* @BeforeEach
  void setUp() {
    underTest.deleteAll();
  } */

  @Test
  public void testThatBookCanBeCreatedAndRecalled() {
    log.info("\n  -- L O G G I N G !! --");
    log.info("  -- Before TestDataUtil.createTestAuthorA()\n  -- Author.findAll: " + authorRepository.findAll().toString());
    Author author = TestDataUtil.createTestAuthorA();
    authorRepository.save(author);    //I use this line because the book's author doesn't have an Id
    Book book = TestDataUtil.createTestBookA(author);
    log.info("  -- Book.findAll: " + underTest.findAll().toString() + "\n");
    log.info("  -- After TestDataUtil.createTestBookA()\n  -- BuiltBook: " + book.toString() + "\n");
    //log.info("BeforeSave\nAuthor: " + author.toString() + ", Book: " + book.toString());
    underTest.save(book);
    //log.info("AfterSave\nAuthor: " + author.toString() + ", Book: " + book.toString());

    Optional<Book> result = underTest.findById(book.getIsbn());
    log.info("Result\nAuthor: " + result.toString() + "\n");
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {  //7. Create the test against the DB data
    Author author = TestDataUtil.createTestAuthorA();
    authorRepository.save(author);

    Book bookA = TestDataUtil.createTestBookA(author);
    underTest.save(bookA);
    Book bookB = TestDataUtil.createTestBookB(author);
    underTest.save(bookB);
    Book bookC = TestDataUtil.createTestBookC(author);
    underTest.save(bookC);

    Iterable<Book> result = underTest.findAll();
    assertThat(result)
            .hasSize(3)
            .contains(bookA, bookB, bookC);
  }

  @Test
  public void testThatBookCanBeUpdated() {
    //create the author for the book
    Author author = TestDataUtil.createTestAuthorA();
    authorRepository.save(author);

    //create the book template, then set the author_id, then create the book row in DB
    Book book = TestDataUtil.createTestBookA(author);
    underTest.save(book);

    //call the new 'update' feat
    book.setTitle("UPDATED");
    underTest.save(book);

    //test/assert the feat
    Optional<Book> result = underTest.findById(book.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatBookCanBeDeleted() {
    Author author = TestDataUtil.createTestAuthorA();
    authorRepository.save(author);

    Book book = TestDataUtil.createTestBookA(author);
    underTest.save(book);

    underTest.delete(book);
    Optional<Book> result = underTest.findById(book.getIsbn());
    assertThat(result).isEmpty();
  }

}
