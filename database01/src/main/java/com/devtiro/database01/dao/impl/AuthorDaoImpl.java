package com.devtiro.database01.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import com.devtiro.database01.dao.AuthorDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

  //done to have access to the JdbcTemplate inside of this class
  private final JdbcTemplate jdbcTemplate;  //can't be reasigned one it's instantiated

}
