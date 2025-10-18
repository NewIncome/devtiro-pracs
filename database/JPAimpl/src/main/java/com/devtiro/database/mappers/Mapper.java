package com.devtiro.database.mappers;

//To encapsulate all of the logic for mapping for our application
public interface Mapper<A, B> {

  B mapTo(A a);

  A mapFrom(B b);

}
