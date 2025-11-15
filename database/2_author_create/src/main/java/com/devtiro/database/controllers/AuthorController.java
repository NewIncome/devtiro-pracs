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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
            .collect(Collectors.toList());  //must return an AuthorDto/Pojo
  }

  //To create the ReadOne end-point
  @GetMapping(path = "/authors/{id}")
  public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
    /* Returns an Optional because it can find it or it won't
    * so it'll return an Optional<Author> or an Optional<> empty */
    Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
    return foundAuthor.map(authorEntity -> { //to convert the Optional to a ResponseEntity
      //in case we found the author, convert AuthorEntity to AuthorDto
      AuthorDto authorDto = authorMapper.mapTo(authorEntity);
      return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
