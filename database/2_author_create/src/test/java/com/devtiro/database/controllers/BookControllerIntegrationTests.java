package com.devtiro.database.controllers;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest  //SpringBootContextLoader as the default ContextLoader when no specific @ContextConfiguratio
/*SpringExtension integrates the Spring TestContext Framework into JUnit 5's Jupiter programming model*/
@ExtendWith(SpringExtension.class)  //to make sure everything is nice and integrated
/*Test annotation which indicates that the ApplicationContext associated with a test is dirty and should therefore be closed and removed from the context cache*/
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  //so our DB is clean after each test
@AutoConfigureMockMvc //so we can handle MVC mocked context
public class BookControllerIntegrationTests {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;  //because we need a jsonObject to create a Book

  private BookService bookService;

  /*Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities*/
  @Autowired
  public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
    this.bookService = bookService;
  }


  @Test
  public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
    BookDto bookDto = TestDataUtil.createTestBookDtoA(null);  //to have the template of a book
    String createBookJson = objectMapper.writeValueAsString(bookDto);//to convert out bookDto to Json

    mockMvc.perform(
        MockMvcRequestBuilders
            .put("/books/" + bookDto.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBookJson)  //for the RequestBody, .content("json String")
    ).andExpect(    //this is how you add assertions; and you need a ResultMatcher
        MockMvcResultMatchers.status().isCreated()
    );
  }

  @Test
  public void testThatCreateBookReturnsCreatedBook() throws Exception {
    BookDto bookDto = TestDataUtil.createTestBookDtoA(null);  //to have the template of a book
    String createBookJson = objectMapper.writeValueAsString(bookDto);//to convert out bookDto to Json

    mockMvc.perform(
        MockMvcRequestBuilders
            .put("/books/" + bookDto.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBookJson)  //for the RequestBody, .content("json String")
    ).andExpect(    //this is how you add assertions; and you need a ResultMatcher
        MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
    );
  }

  //To test the ReadMany endpoints(List)
  @Test
  public void testThatListBookReturnsHttpStatus200Ok() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders
            .get("/books")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(    //this is how you add assertions; and you need a ResultMatcher
        MockMvcResultMatchers.status().isOk()
    );
  }

  //Now modified for pagination
  @Test
  public void testThatListBooksPageSuccessfullyReturnsPageOfBooks() throws Exception {
    BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
    bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

    mockMvc.perform(
        MockMvcRequestBuilders
            .get("/books")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(    //this is how you add assertions; and you need a ResultMatcher
        MockMvcResultMatchers.jsonPath("$.content[0].isbn").value("978-1-2345-6789-0")
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.content[0].title").value("The Shadow in the Attic")
    );
  }

  @Test
  public void testThatGetBookReturnsHttp200WhenAuthorExists() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

    mockMvc.perform(
      MockMvcRequestBuilders
          .get("/books/" + testBookEntity.getIsbn())
          .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
        MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatGetBookReturnsHttp404WhenNoAuthorExists() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

    mockMvc.perform(
        MockMvcRequestBuilders
            .get("/books/1")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
        MockMvcResultMatchers.status().isNotFound()
    );
  }

  @Test
  public void testThatGetBookReturnsBookWhenBookExists() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

    mockMvc.perform(
        MockMvcRequestBuilders
            .get("/books/" + testBookEntity.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn())
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value(testBookEntity.getTitle())
    );
  }

  @Test
  public void testThatFullUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    BookEntity savedBookEntity = bookService.createUpdateBook(
        testBookEntity.getIsbn(), testBookEntity
    );

    BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
    testBookDto.setIsbn(savedBookEntity.getIsbn());  //to make sure the isbn is correct
    String updatedBookJson = objectMapper.writeValueAsString(testBookDto);

    mockMvc.perform(
        MockMvcRequestBuilders
                  .put("/books/" + savedBookEntity.getIsbn())
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(updatedBookJson)
    ).andExpect(
        MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatFullUpdateBookReturnsUpdatedBook() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    BookEntity savedBookEntity = bookService.createUpdateBook(
        testBookEntity.getIsbn(), testBookEntity
    );

    BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
    testBookDto.setIsbn(savedBookEntity.getIsbn());  //to make sure the isbn is correct
    testBookDto.setTitle("Updated");
    String updatedBookJson = objectMapper.writeValueAsString(testBookDto);

    mockMvc.perform(
        MockMvcRequestBuilders
            .put("/books/" + savedBookEntity.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBookJson)
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn())
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value("Updated")
    );
  }

  @Test
  public void testThatPartialUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
    //Create a book in the testDB
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    BookEntity savedBookEntity = bookService.createUpdateBook(
        testBookEntity.getIsbn(), testBookEntity
    );

    //Create different test bookDTO to use it's info, with a modified value jit
    BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
    //test a change
    testBookDto.setTitle("Updated!");
    //create the json string to send in the request. Needs to check for 'JsonProcessingException'
    String updatedBookJson = objectMapper.writeValueAsString(testBookDto);

    //make the mock-request and assert with resultMatchers. Needs to check for general 'Exception'
    mockMvc.perform(
        MockMvcRequestBuilders
            .patch("/books/" + savedBookEntity.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBookJson)
    ).andExpect(
        MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  public void testThatPartialUpdateBookReturnsUpdatedBookWhenBookExists() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    BookEntity savedBookEntity = bookService.createUpdateBook(
        testBookEntity.getIsbn(), testBookEntity
    );

    BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
    testBookDto.setTitle("Updated!");
    String updatedBookJson = objectMapper.writeValueAsString(testBookDto);

    mockMvc.perform(
        MockMvcRequestBuilders
            .patch("/books/" + savedBookEntity.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBookJson)
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn())
    ).andExpect(
        MockMvcResultMatchers.jsonPath("$.title").value("Updated!")
    );
  }

  @Test
  public void testThatDeleteBookReturnsHttpStatus204WhenNoBookExists() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders
            .delete("/books/99")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect( MockMvcResultMatchers.status().isNoContent() );
  }

  @Test
  public void testThatDeleteBookReturnsHttpStatus204WhenBookExists() throws Exception {
    BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
    bookService.createUpdateBook(testBookEntity.getTitle(), testBookEntity);

    mockMvc.perform(
        MockMvcRequestBuilders
            .delete("/books/99")
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect( MockMvcResultMatchers.status().isNoContent() );
  }

}
