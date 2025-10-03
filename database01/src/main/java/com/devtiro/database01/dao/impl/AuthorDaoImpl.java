package com.devtiro.database01.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import com.devtiro.database01.dao.AuthorDao;
import com.devtiro.database01.domain.Author;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

  //done to have access to the JdbcTemplate inside of this class
  private final JdbcTemplate jdbcTemplate;  //can't be reasigned one it's instantiated

  @Override
  public void create(Author author) {
    
    jdbcTemplate.update(
              "INSERT INTO authors (id, anme, age) VALUES (?, ?, ?)",
              author.getId(), author.getName(), author.getAge()
    );
    
  }

}
