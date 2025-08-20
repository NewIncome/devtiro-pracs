package com.devtiro.database01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@SpringBootApplication
@Log
public class Database01Application implements CommandLineRunner {

  //ConstructorInjection of DataSource
  private final DataSource dataSource;

  public Database01Application(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  //Main method
	public static void main(String[] args) { SpringApplication.run(Database01Application.class, args); }

  @Override
  public void run(final String... args) {
    log.info("Datasource: " + dataSource.toString());
    final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
    restTemplate.execute("select 1");
  }
}
