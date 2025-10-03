package com.devtiro.database01.dao.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.devtiro.database01.domain.Author;

//Unit Tests
@ExtendWith(MockitoExtension.class)
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
    Author author = Author.builder()
            .id(1L)
            .name("Abigail Rose")
            .age(80)
            .build();
    
    underTest.create(author);

    verify(jdbcTemplate).update(
            eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
            eq(1L), eq("Abigail Rose"), eq(80)
    );
    //mockito works better with argument matchers(like eq()) than raw values
  }
}
