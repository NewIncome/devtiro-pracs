package com.devtiro.database01.dao;

import java.util.List;
import java.util.Optional;

import com.devtiro.database01.domain.Book;

public interface BookDao {

  //Step #2 in TDD. 3 in BookDaoImpl
  void create(Book book);

  //T2 TDD2
  Optional<Book> findOne(String isbn);

  List<Book> find();  //3. Declare the new method in the domain. Test for error for no-implementation. Then implement

}
