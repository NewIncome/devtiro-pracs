package com.devtiro.database01.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.devtiro.database01.dao.BookDao;
import com.devtiro.database01.domain.Book;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

  private final JdbcTemplate jdbcTemplate;

  //Step #3 in TDD. 4 in BookDaoImplTest
  @Override
  public void create(Book book) {
    //Step #5 in TDD. & Run test to PASS
    jdbcTemplate.update(
          "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
          book.getIsbn(),
          book.getTitle(),
          book.getAuthorId()
    );
  }

  //T2 TDD3
  @Override
  public Optional<Book> findOne(String isbn) {
    //T2 TDD7
    List<Book> results = jdbcTemplate.query(
        "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
        new BookRowMapper(),
        isbn
    );

    return results.stream().findFirst();
  }

  @Override
  public List<Book> find() {  //4. Implement the new method, empty. Then go to test and pre-test the complete implementation
    //6. Code the implementation. Test to pass!!  Next. Create the IntegrationTest
    return jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books",
                new BookRowMapper()
    );
  }

  @Override
  public void update(String isbn, Book book) {
    jdbcTemplate.update(
          "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
          book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn
    );
  }

  //T2 TDD5
  public static class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Book.builder()
              .isbn(rs.getString("isbn"))
              .title(rs.getString("title"))
              .authorId(rs.getLong("author_id"))
              .build();
    }
    
  }

}
