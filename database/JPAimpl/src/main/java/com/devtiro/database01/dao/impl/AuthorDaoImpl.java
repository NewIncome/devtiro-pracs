package com.devtiro.database01.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import com.devtiro.database01.dao.AuthorDao;
import com.devtiro.database01.domain.Author;

import lombok.RequiredArgsConstructor;

@Component
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
  public Optional<Author> findOne(long authorId) {
    //TDD7
    List<Author> results = jdbcTemplate.query(
            "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
            new AuthorRowMapper(),
            authorId);
    return results.stream().findFirst();
  }

  @Override
  public List<Author> find() {
    return jdbcTemplate.query(
          "SELECT id, name, age FROM authors",
          new AuthorRowMapper()
    );
  }

  @Override
  public void update(long id, Author author) {
    jdbcTemplate.update(
        "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
        author.getId(), author.getName(), author.getAge(), id
    );
  }

  @Override
  public void delete(long id) {
    jdbcTemplate.update(
        "DELETE FROM authors WHERE id = ?",
        1L
    );
  }

  public static class AuthorRowMapper implements RowMapper <Author> {
    /*
     * Because we are using Jdbc and DAO pattern,
     * we need to handle the conversion to-and-from SQL and Java objects our selfs
     * For this we can use RowMappers, ResultSetExtractors, RowBackHandlers
     */

    //TDD5
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Author.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .build();
    }
    
  }

}
