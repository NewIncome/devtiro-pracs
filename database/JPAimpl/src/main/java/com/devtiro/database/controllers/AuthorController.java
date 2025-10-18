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

import org.springframework.web.bind.annotation.RestController;

import com.devtiro.database.domain.Author;
import com.devtiro.database.services.AuthorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//It's need to work on all our EndPoints
@RestController
public class AuthorController {

  //Injecting the AuthorService to further use it
  private AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }


  @PostMapping(path = "/authors")
  public Author createAuthor(@RequestBody Author author) {
    //call to the ServiceLayer

    return authorService.createAuthor(author);
  }
  
}
