package com.devtiro.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.domain.entities.BookEntity;

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
    AuthorEntity author = TestDataUtil.createTestAuthorA();

    //The Author will be created by the Cascading
    BookEntity book = TestDataUtil.createTestBookA(author);
    log.info("  -- Book.findAll: " + underTest.findAll().toString() + "\n");
    log.info("  -- After TestDataUtil.createTestBookA()\n  -- BuiltBook: " + book.toString() + "\n");
    //log.info("BeforeSave\nAuthor: " + author.toString() + ", Book: " + book.toString());
    underTest.save(book);
    //log.info("AfterSave\nAuthor: " + author.toString() + ", Book: " + book.toString());

    Optional<BookEntity> result = underTest.findById(book.getIsbn());
    log.info("Result\nAuthor: " + result.toString() + "\n");
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatMultipleBooksCanBeCreatedAndRecalled() {  //7. Create the test against the DB data
    AuthorEntity author = TestDataUtil.createTestAuthorA();

    //The Author will be created by the Cascading
    BookEntity bookA = TestDataUtil.createTestBookA(author);
    underTest.save(bookA);
    BookEntity bookB = TestDataUtil.createTestBookB(author);
    underTest.save(bookB);
    BookEntity bookC = TestDataUtil.createTestBookC(author);
    underTest.save(bookC);

    Iterable<BookEntity> result = underTest.findAll();
    assertThat(result)
            .hasSize(3)
            .contains(bookA, bookB, bookC);
  }

  @Test
  public void testThatBookCanBeUpdated() {
    //create the author for the book
    AuthorEntity author = TestDataUtil.createTestAuthorA();

    //The Author will be created by the Cascading
    BookEntity book = TestDataUtil.createTestBookA(author);
    underTest.save(book);

    //call the new 'update' feat
    book.setTitle("UPDATED");
    underTest.save(book);

    //test/assert the feat
    Optional<BookEntity> result = underTest.findById(book.getIsbn());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(book);
  }

  @Test
  public void testThatBookCanBeDeleted() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();

    //The Author will be created by the Cascading
    BookEntity book = TestDataUtil.createTestBookA(author);
    underTest.save(book);

    underTest.deleteById(book.getIsbn());
    Optional<BookEntity> result = underTest.findById(book.getIsbn());
    assertThat(result).isEmpty();
  }

}
