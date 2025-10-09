package com.devtiro.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder        //...explain...
@Entity   //label object as an Entity that can be used with Soring Data Jpa
@Table(name = "authors")  //to define which table it maps to in the DB
public class Author {
  
  //with long it would default to '0', with object it defaults to 'null'
  @Id   //to define the field as the PrimaryKey of the entity/table
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq") //auto-generate id's
  private Long id;

  private String name;

  private Integer age;

}
