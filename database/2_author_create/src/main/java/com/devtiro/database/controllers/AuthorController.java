/**
 * AuthorController End-Points
 *  POST    /authors        //Create 1 author; response->HTTP:201 + authorJson
 *  GET     /authors/{id}   //Read 1 author; response-> HTTP:200 + authorJson
 *  GET     /authors          //ReadMany authors; always response-> HTTP:200 + emptyList || authorListJson
 *  PUT     /authors/{id}   //FullUpdate; response-> HTTP:200 + authorJson
 *  PATCH   /authors/{id}   //PartialUpdate; response-> HTTP:200 + authorJson
 *  DELETE  /authors/{id}   //Delete; response-> HTTP:204 + noBody
 */
package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.mappers.Mapper;
import com.devtiro.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

  private AuthorService authorService;

  private Mapper<AuthorEntity, AuthorDto> authorMapper;

  public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
      this.authorService = authorService;
      this.authorMapper = authorMapper;
  }

  @PostMapping(path = "/authors")
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
      AuthorEntity authorEntity = authorMapper.mapFrom(author);
      AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
      return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
  }
  /* ResponseEntity<> allows to control things like the StatusCode of the response
   *   new ResponseEntity<>(ourObject, HttpStatus.CREATED)
   */

  //To create the ReadMany-endpoints(List)
  @GetMapping("/authors")
  public List<AuthorDto> listAuthors() {
    List<AuthorEntity> authors = authorService.findAll();  //that's what we receive from querying the DB
    return authors.stream()
            .map(authorMapper::mapTo)
            .collect(Collectors.toList());
  }

}
