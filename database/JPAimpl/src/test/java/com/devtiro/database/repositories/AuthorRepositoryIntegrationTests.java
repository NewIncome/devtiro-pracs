package com.devtiro.database.repositories;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest   // Loads Spring context, includes @SpringExtension. To startup a test version of our application when our test runs
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  //Use when a test alters the Spring application context
public class AuthorRepositoryIntegrationTests {

  private AuthorRepository underTest; //reference to the class under testing

  @Autowired    //Annotation necessary in tests
  public AuthorRepositoryIntegrationTests(AuthorRepository underTest) { //Constructor Injection
    this.underTest = underTest;
  }

/* //Not needed in this case since test/application.properties is working ok now  
  @BeforeEach
  void setUp() {
    underTest.deleteAll();
  }*/

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {

    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    Optional<Author> result = underTest.findById(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    Author authorA = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);    //creates the author in the DB
    Author authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);

    Iterable<Author> result = underTest.findAll();
    assertThat(result)
            .hasSize(3)
            .containsExactly(authorA, authorB, authorC);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);

    author.setName("UPDATED");
    underTest.save(author);
    Optional<Author> result = underTest.findById(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.save(author);

    underTest.deleteById(author.getId());
    Optional<Author> result = underTest.findById(author.getId());
    assertThat(result).isEmpty();
  }

  @Test
  public void testThatGetAuthorWithAgeLessThan() {
    Author testAuthorA = TestDataUtil.createTestAuthorA();
    underTest.save(testAuthorA);
    Author testAuthorB = TestDataUtil.createTestAuthorB();
    underTest.save(testAuthorB);
    Author testAuthorC = TestDataUtil.createTestAuthorC();
    underTest.save(testAuthorC);

    Iterable<Author> result = underTest.ageLessThan(50);
    assertThat(result).containsExactly(testAuthorA, testAuthorC);
  }

}
