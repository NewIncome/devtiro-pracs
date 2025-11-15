package com.devtiro.database.services.impl;

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

}
