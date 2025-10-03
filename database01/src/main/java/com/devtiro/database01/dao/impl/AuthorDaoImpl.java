package com.devtiro.database01.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

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
              "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
              author.getId(), author.getName(), author.getAge()
    );
    
  }

  //TDD3
  @Override
  public Optional<Author> findOne(long l) {
    // TODO Auto-generated method stub
    return Optional.empty();
  }

  public static class AuthorRowMapper implements RowMapper <Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
      // TODO Auto-generated method stub
      return Author.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .build();
    }
    
  }

}
