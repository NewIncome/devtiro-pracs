package com.devtiro.database.services;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

  AuthorEntity createAuthor(AuthorEntity authorEntity);

  List<AuthorEntity> findAll();

  Optional<AuthorEntity> findOne(Long id);

  boolean isExists(Long id);

  AuthorEntity update(AuthorEntity authorEntity);

  AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

}
