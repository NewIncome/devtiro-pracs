package com.devtiro.database01.dao;

import com.devtiro.database01.domain.Book;

public interface BookDao {

  //Step #2 in TDD. 3 in BookDaoImpl
  void create(Book book);

}
