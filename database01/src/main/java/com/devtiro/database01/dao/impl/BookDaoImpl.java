package com.devtiro.database01.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import com.devtiro.database01.dao.BookDao;
import com.devtiro.database01.domain.Book;

import lombok.RequiredArgsConstructor;

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

}
