package com.devtiro.database01.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import com.devtiro.database01.dao.BookDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

  private final JdbcTemplate jdbcTemplate;

}
