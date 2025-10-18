package com.devtiro.database.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {

  @Id
  private String isbn;

  private String title;

  @ManyToOne(cascade = CascadeType.ALL)   //to specify the relationship, and to be able to get the authorObj when we call the bookObj
  @JoinColumn(name = "author_id")   //to set what column is used to join the field with the other table/object
  private AuthorEntity author;

}
