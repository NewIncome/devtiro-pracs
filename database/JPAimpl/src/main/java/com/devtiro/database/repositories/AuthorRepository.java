package com.devtiro.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devtiro.database.domain.entities.AuthorEntity;

@Repository   //
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
  /* Spring JPA is able to workout(auto-implement) custom queries based on just:
    "how you name your methods" */
  Iterable<AuthorEntity> ageLessThan(int age);

  @Query("SELECT a from Author a where a.age > ?1")
  Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);

}
