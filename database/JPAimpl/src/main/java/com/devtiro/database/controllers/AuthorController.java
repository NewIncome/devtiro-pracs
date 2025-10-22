/**
 * EndPoints to be worked by out Controller:
 * 
 *  POST    /authors        //Create 1 author; response->HTTP:201 + authorJson
 *  GET     /authors/{id}   //Read 1 author; response-> HTTP:200 + authorJson
 *  GET     /authors          //ReadMany authors; always response-> HTTP:200 + emptyList || authorListJson
 *  PUT     /authors/{id}   //FullUpdate; response-> HTTP:200 + authorJson
 *  PATCH   /authors/{id}   //PartialUpdate; response-> HTTP:200 + authorJson
 *  DELETE  /authors/{id}   //Delete; response-> HTTP:204 + noBody
 */
package com.devtiro.database.controllers;

import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.mappers.Mapper;
import org.springframework.web.bind.annotation.RestController;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.services.AuthorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//It's need to work on all our EndPoints
@RestController
public class AuthorController {

  //Injecting the AuthorService to further use it
  private AuthorService authorService;

  private Mapper<AuthorEntity, AuthorDto> authorMapper;

  public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
  }


    /* It's ok for the Service-layer to deal with Entities, it's your business logic
       so some knowledge of the underlying entities that the persistence layer uses
       It's debatable and up to the Coder
       But a rule is to definitely seperate the Presentation, Service & Persistance layers

       Therefor we need to convert our Dto to an Entity in this class
     */
    //call to the ServiceLayer
  @PostMapping(path = "/authors")
  public AuthorDto createAuthor(@RequestBody AuthorDto author) {
    AuthorEntity authorEntity = authorMapper.mapFrom(author);
    AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
    return authorMapper.mapTo(savedAuthorEntity);
  }
  
}
