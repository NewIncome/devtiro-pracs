package com.devtiro.database.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.entities.AuthorEntity;

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

    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    AuthorEntity authorA = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);    //creates the author in the DB
    AuthorEntity authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    AuthorEntity authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);

    Iterable<AuthorEntity> result = underTest.findAll();
    assertThat(result)
            .hasSize(3)
            .containsExactly(authorA, authorB, authorC);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);

    author.setName("UPDATED");
    underTest.save(author);
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    AuthorEntity author = TestDataUtil.createTestAuthorA();
    underTest.save(author);

    underTest.deleteById(author.getId());
    Optional<AuthorEntity> result = underTest.findById(author.getId());
    assertThat(result).isEmpty();
  }

  @Test
  public void testThatGetAuthorWithAgeLessThan() {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    underTest.save(testAuthorA);
    AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
    underTest.save(testAuthorB);
    AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
    underTest.save(testAuthorC);

    Iterable<AuthorEntity> result = underTest.ageLessThan(50);
    assertThat(result).containsExactly(testAuthorA, testAuthorC);
  }

  @Test
  public void testThatGetAuthorWithAgeGreaterThan() {
    AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
    underTest.save(testAuthorA);
    AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
    underTest.save(testAuthorB);
    AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
    underTest.save(testAuthorC);

    Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
    assertThat(result).containsExactly(testAuthorA);
  }

}
