package com.devtiro.database01.dao.impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.transaction.annotation.Transactional;

import com.devtiro.database01.TestDataUtil;
import com.devtiro.database01.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

//@Transactional    //Manages database transactions and rollbacks
@SpringBootTest   // Loads Spring context, includes @SpringExtension. To startup a test version of our application when our test runs
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  //Use when a test alters the Spring application context
public class AuthorDaoImplIntegrationTests {

  private AuthorDaoImpl underTest; //reference to the class under testing

  @Autowired    //Annotation necessary in tests
  public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) { //Constructor Injection
    this.underTest = underTest;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {

    Author author = TestDataUtil.createTestAuthorA();
    underTest.create(author);
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
    /* .isEqualTo() ,  Becuase we're using lombok it'll also compare every instance variable
    of the given class agains the comparable class */
  }

  @Test
  public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
    Author authorA = TestDataUtil.createTestAuthorA();
    underTest.create(authorA);    //creates the author in the DB
    Author authorB = TestDataUtil.createTestAuthorB();
    underTest.create(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    underTest.create(authorC);

    List<Author> result = underTest.find();
    assertThat(result)
            .hasSize(3)
            .containsExactly(authorA, authorB, authorC);
  }

  @Test
  public void testThatAuthorCanBeUpdated() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.create(author);

    author.setName("UPDATED");
    underTest.update(author.getId(), author);
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
  }

  @Test
  public void testThatAuthorCanBeDeleted() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.create(author);

    underTest.delete(author.getId());
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isEmpty();
  }

}
