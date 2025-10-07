package com.devtiro.database01.dao.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.devtiro.database01.TestDataUtil;
import com.devtiro.database01.domain.Author;

//Unit Tests
@ExtendWith(MockitoExtension.class)
@Transactional
public class AuthorDaoImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  /*
   * @Mock & @InjectMocks
   * 
   * • Thanks to @InjectMocks
   * Before each test is run a new instance of the AuthorDaoImpl is created for us
   * • and thanks to @Mock
   * Also a new instance of jdbcTemplate is created, and 'injected' to
   * the previously created AuthorDaoImpl instance
   */
  @InjectMocks
  private AuthorDaoImpl underTest;
  
  @Test
  public void testThatCreatesAuthorAndGeneratesCorrectSql() {
    Author author = TestDataUtil.createTestAuthorA();
    
    underTest.create(author);

    verify(jdbcTemplate).update(
            eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
            eq(1L), eq("Abigail Rose"), eq(80)
    );
    //mockito works better with argument matchers(like eq()) than raw values
  }

  @Test   //TDD1
  public void testThatFindOneGeneratesTheCorrectSql() {
    underTest.findOne(1L);

    //TDD4
    verify(jdbcTemplate).query(
      eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
      ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(), //TDD6
      eq(1L)
    );
  }

  @Test
  public void testThatFindManyGeneratesTheCorrectSql() {
    underTest.find();
    verify(jdbcTemplate).query(
            eq("SELECT id, name, age FROM authors"),
            ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()   // to specify Type format
    );
  }

  @Test
  public void testThatFullUpdateGeneratesTheCorrectSql() {
    Author author = TestDataUtil.createTestAuthorA();
    underTest.update(3L, author);

    verify(jdbcTemplate).update(
        "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
        1L, "Abigail Rose", 80, 3L
    );
  }
}
