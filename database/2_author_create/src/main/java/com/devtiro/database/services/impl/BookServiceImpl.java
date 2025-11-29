package com.devtiro.database.services.impl;

import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.repositories.BookRepository;
import com.devtiro.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public BookEntity createUpdateBook(String isbn, BookEntity book) {
    book.setIsbn(isbn);
    return bookRepository.save(book);
  }

  @Override
  public List<BookEntity> findAll() {
    //bookRepository.findAll(); //returns an Iterable<T>, so we need to:
    return StreamSupport
              .stream(
                    bookRepository.findAll().spliterator(),
                    false)
              .collect(Collectors.toList());
  }

  @Override
  public Page<BookEntity> findAll(Pageable pageable) {
    return bookRepository.findAll(pageable);
  }

  @Override
  public Optional<BookEntity> findOne(String isbn) {
    return bookRepository.findById(isbn);
  }

  @Override
  public boolean isExists(String isbn) {
    return bookRepository.existsById(isbn);
  }

  @Override
  public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
    //set the id/isbn to make sure we are targeting the correct bookEntity to update
    bookEntity.setIsbn(isbn);
    //get the objectToBeUpdated from the DB, to updateIt
    return bookRepository.findById(isbn).map(foundBook -> {
              Optional.ofNullable(bookEntity.getTitle()).ifPresent(foundBook::setTitle);
              return bookRepository.save(foundBook);
    }).orElseThrow(() -> new RuntimeException("Book does not exist"));
  }

  @Override
  public void delete(String isbn) {
    bookRepository.deleteById(isbn);
  }

}
