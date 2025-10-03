package com.devtiro.database01.dao;

import java.util.Optional;

import com.devtiro.database01.domain.Author;

public interface AuthorDao {

  void create(Author author);

  //TDD2
  Optional<Author> findOne(long l); //for type safe, to return an empty Optional instead of null

}
