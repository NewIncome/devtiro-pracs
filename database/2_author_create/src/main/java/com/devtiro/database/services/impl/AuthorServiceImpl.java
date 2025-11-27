package com.devtiro.database.services.impl;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.repositories.AuthorRepository;
import com.devtiro.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

  private AuthorRepository authorRepository;

  public AuthorServiceImpl(AuthorRepository authorRepository) {
      this.authorRepository = authorRepository;
  }

  @Override
  public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

  @Override
  public List<AuthorEntity> findAll() {
    //authorRepository.findAll(); //it returns an iterable, to make it a list we do:
    return StreamSupport    /*Low-level utility methods for creating and manipulating streams*/
              .stream(      /*Creates a new sequential or parallel Stream from a Spliterator*/
                  authorRepository      /*the class that extends the CrudRepository class querying-functionality*/
                        .findAll()      /*returns an iterable; extended CrudRepository-class-method that Returns all instances of the type*/
                        .spliterator(), /*Creates a Spliterator over the elements described by this Iterable*/
                  false)    /*to define the stream as true=parallel, false=sequential*/
              .collect(Collectors.toList());  //to collect/convert the resulting stream into a List
  }

  @Override
  public Optional<AuthorEntity> findOne(Long id) {  //is just a pass through in this case
    return authorRepository.findById(id);
  }

  @Override
  public boolean isExists(Long id) {
    return authorRepository.existsById(id);
  }

  @Override
  public AuthorEntity update(AuthorEntity authorEntity) {
    return authorRepository.save(authorEntity);
  }

  @Override
  public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
    authorEntity.setId(id);
    /* In order to update the DB, 1st we need to retrieve the DB then update */
    return authorRepository.findById(id).map(existingAuthor -> {
      /* If the Optional has an Age and is notNull,
         set that as we found it on the existing author as we found it in the DB */
      //.ofNullable(), returns an emptyOptional on Null; .of() doesn't
      Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
      Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
      return authorRepository.save(existingAuthor);
    }).orElseThrow(() -> new RuntimeException("Author does not exist"));
  }

  @Override
  public void delete(Long id) {
    authorRepository.deleteById(id);
  }

}
