package com.devtiro.database01.dao.impl;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devtiro.database01.TestDataUtil;
import com.devtiro.database01.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest   // Loads Spring context, includes @SpringExtension. To startup a test version of our application when our test runs
public class AuthorDaoImplIntegrationTests {

  private AuthorDaoImpl underTest; //reference to the class under testing

  @Autowired    //Annotation necessary in tests
  public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) { //Constructor Injection
    this.underTest = underTest;
  }

  @Test
  public void testThatAuthorCanBeCreatedAndRecalled() {

    Author author = TestDataUtil.createTestAuthor();
    underTest.create(author);
    Optional<Author> result = underTest.findOne(author.getId());
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(author);
    /* .isEqualTo() ,  Becuase we're using lombok it'll also compare every instance variable
    of the given class agains the comparable class */

  }

}
