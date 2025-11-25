/**
 * BookController End-Points
 *    PUT     /books/{isbn}   //Create 1 book; response->HTTP:201 + bookJson
 *    GET     /books/{isbn}   //Read 1 book; response-> HTTP:200 + bookJson
 *    GET     /books          //ReadMany books; always response-> HTTP:200 + emptyList || bookListJson
 *    PUT     /books/{isbn}   //Update; response-> HTTP:200 + bookJson
 *    PATCH   /books/{isbn}   //PartialUpdate; response-> HTTP:200 + bookJson
 *    DELETE  /books/{isbn}   //Delete; response-> HTTP:204 + noBody
 */
package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.mappers.Mapper;
import com.devtiro.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

  private Mapper<BookEntity, BookDto> bookMapper;

  private BookService bookService;

  public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
    this.bookMapper = bookMapper;
    this.bookService = bookService;
  }

  @PutMapping("/books/{isbn}")  //for a FULL-UPDATE or CREATE(w-CustomId)
  public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn,
                                            @RequestBody BookDto bookDto) { //variable to assign PathVariable to
    /* to map our Dto to our Entity to use it in our Service for our PersistenceLayer
       mapDtoToEntity > Service > PersistenceLayer */
    BookEntity bookEntity = bookMapper.mapFrom(bookDto);
    boolean bookExists = bookService.isExists(isbn); //check before create/update
    //to create a Book we need to interact with our service layer
    BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
    //we need to return a BookDto from this
    BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

    if(bookExists) { //update
      return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
    } else { //create
      return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }
  }

  @GetMapping("/books")
  public List<BookDto> listBooks() {
    List<BookEntity> books = bookService.findAll(); //use pagination later
    return books.stream()
                  .map(bookMapper::mapTo)
                  .collect(Collectors.toList());
  }

  @GetMapping("/books/{isbn}")
  public ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
    //How does JACKSON do the magic of CONVERTING from JSON to BOOKDTO ???
    Optional<BookEntity> foundBook = bookService.findOne(isbn);
    return foundBook.map(bookEntity -> {
      BookDto bookDto = bookMapper.mapTo(bookEntity);
      return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
